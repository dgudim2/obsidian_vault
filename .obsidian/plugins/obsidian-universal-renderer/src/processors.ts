import { MarkdownPostProcessorContext } from 'obsidian';
import * as os from 'os';
import * as path from 'path';
import * as fs from 'fs';
import { spawn } from 'child_process';
import GraphvizPlugin from './main';

import { GraphvizSettings } from './setting';

import * as crypto from 'crypto';
const md5 = (contents: string) => crypto.createHash('md5').update(contents).digest('hex');

type RenderType = 'dot' | 'latex' | 'ditaa' | 'blockdiag' | 'refgraph';

const svgColorMap = new Map<string, string>([

    // dark colors
    ['darkred', '--g-color-dark-red'],
    ['firebrick', '--g-color-dark-red'],
    ['maroon', '--g-color-dark-red'],
    ['brown', '--g-color-dark-red'],
    ['darkred', '--g-color-dark-red'],

    ['darkmagenta', '--g-color-dark-purple'],
    ['darkviolet', '--g-color-dark-purple'],
    ['blueviolet', '--g-color-dark-purple'],
    ['darkorchid', '--g-color-dark-purple'],
    ['indigo', '--g-color-dark-purple'],

    ['darkgreen', '--g-color-dark-green'],

    ['darkblue', '--g-color-dark-blue'],

    ['chocolate', '--g-color-dark-orange'],

    ['goldenrod', '--g-color-dark-yellow'],

    ['darkcyan', '--g-color-dark-aqua'],

    // neutral colors
    ['red', '--g-color-red'],
    ['purple', '--g-color-purple'],
    ['green', '--g-color-green'],
    ['blue', '--g-color-blue'],
    ['darkorange', '--g-color-orange'],
    ['yellow', '--g-color-yellow'],
    ['cyan', '--g-color-aqua'],

    // light colors
    ['tomato', '--g-color-light-red'],
    ['lightcoral', '--g-color-light-red'],
    ['indianred', '--g-color-light-red'],

    ['magenta', '--g-color-light-purple'],
    ['lightgreen', '--g-color-light-green'],
    ['lightblue', '--g-color-light-blue'],

    ['orange', '--g-color-light-orange'],
    ['coral', '--g-color-light-orange'],

    ['gold', '--g-color-light-yellow'],
    ['cyan', '--g-color-light-aqua'],


    // gray colors

    ['ghostwhite', '--g-color-light100-hard'], // #F9F5D7
    ['white', '--g-color-light100'],           // #FBF1C7
    ['seashell', '--g-color-light100-soft'],   // #F2E5BC
    ['snow', '--g-color-light90'],             // #EBDBB2                    
    ['whitesmoke', '--g-color-light80'],       // #D5C4A1
    ['lightgray', '--g-color-light70'],        // #BDAE93
    ['silver', '--g-color-light60'],           // #A89984

    //['--g-color-dark100-hard']               // #1D2021 unused
    ['black', '--g-color-dark100'],            // #282828
    ['dimgray', '--g-color-dark100-soft'],     // #32302F
    ['darkslategray', '--g-color-dark90'],     // #3C3836
    ['slategray', '--g-color-dark80'],         // #504945
    ['lightslategray', '--g-color-dark70'],    // #665C54
    ['gray', '--g-color-dark60'],              // #7C6F64

    ['darkgray', '--g-color-gray']            // #928374
]);

export class Processors {
    pluginSettings: GraphvizSettings;
    renderTypeMapping: Map<RenderType, (source: string, el: HTMLElement, ctx: MarkdownPostProcessorContext) => Promise<unknown> | void>;

    referenceGraphMap: Map<string, string> = new Map();

    constructor(plugin: GraphvizPlugin) {
        this.pluginSettings = plugin.settings;
        this.renderTypeMapping = new Map<RenderType, (source: string, el: HTMLElement, ctx: MarkdownPostProcessorContext) => Promise<unknown> | void>([
            ['latex', this.latexProcessor],
            ['dot', this.dotProcessor],
            ['ditaa', this.ditaaProcessor],
            ['blockdiag', this.blockdiagProcessor],
            ['refgraph', this.refgraphProcessor]
        ]);
    }

    private getRendererParameters(type: RenderType, sourceFile: string, outputFile: string): [string, string[]] {
        switch (type) {
            case 'dot':
                return [this.pluginSettings.dotPath, ['-Tsvg', sourceFile, '-o', outputFile]];
            case 'latex':
                return [this.pluginSettings.pdflatexPath, ['-shell-escape', '-output-directory', this.getTempDir(type), sourceFile]];
            case 'ditaa':
                return [this.pluginSettings.ditaaPath, [sourceFile, '--transparent', '--svg', '--overwrite']];
            case 'blockdiag':
                return [this.pluginSettings.blockdiagPath, ['--antialias', '-Tsvg', sourceFile, '-o', outputFile]];
        }
    }

    private spawnProcess(cmdPath: string, parameters: string[]): Promise<string> {
        return new Promise<string>((resolve, reject) => {
            console.debug(`Starting external process ${cmdPath}, ${parameters}`);
            const process = spawn(cmdPath, parameters);
            let errData = '';
            process.stderr.on('data', (data) => { errData += data; });
            process.on('error', (err: Error) => reject(`"${cmdPath} ${parameters}" failed, ${err}`));
            process.stdin.end();

            process.on('exit', (code) => {
                if (code !== 0) {
                    return reject(`"${cmdPath} ${parameters}" failed, error code: ${code}, stderr: ${errData}`);
                }
                resolve('ok');
            });
        });
    }

    private async writeRenderedFile(sourceFile: string, outputFile: string, type: RenderType): Promise<string> {

        const [cmdPath, params] = this.getRendererParameters(type, sourceFile, outputFile);

        await this.spawnProcess(cmdPath, params);
        if (type === 'latex') {
            await this.spawnProcess(this.pluginSettings.pdf2svgPath, [`${sourceFile}.pdf`, outputFile]);
        }

        const imageData = this.makeDynamicSvg(fs.readFileSync(outputFile).toString());
        fs.unlinkSync(outputFile);
        fs.writeFileSync(outputFile, imageData);

        return outputFile;
    }

    private getTempDir(type: RenderType): string {
        return path.join(os.tmpdir(), `obsidian-${type}`);
    }

    private makeDynamicSvg(svg_source: string) {

        for (const [color, target_var] of svgColorMap) {
            svg_source = svg_source.replaceAll(`"${color}"`, `"var(${target_var})"`);
        }
        return svg_source;
    }

    private parseFrontMatter(source: string, outputFile: string) {
        if (source.startsWith('---')) {
            const lastIndex = source.indexOf('---', 3);
            const frontMatter = source.substring(source.indexOf('---') + 3, lastIndex);
            const parameters = frontMatter.trim().split('\n');
            for (const parameter of parameters) {
                const parameter_split = parameter.split(':');
                const parameter_name = parameter_split[0].trim();
                const parameter_value = parameter_split[1].trim();
                switch (parameter_name) {
                    case 'ref-name':
                        this.referenceGraphMap.set(parameter_value, outputFile);
                }
            }
            return source.substring(lastIndex + 3);
        }
        return source;
    }

    private async convertToImage(type: RenderType, source: string): Promise<string> {

        if (type === 'refgraph') {
            return this.referenceGraphMap.get(source.trim());
        }

        const temp_dir = this.getTempDir(type);
        const graph_hash = md5(source);
        const inputFile = path.join(temp_dir, graph_hash);
        const outputFile = `${inputFile}.svg`;

        if (!fs.existsSync(temp_dir)) {
            fs.mkdirSync(temp_dir);
        }

        source = this.parseFrontMatter(source, outputFile);

        if (!fs.existsSync(inputFile)) {
            fs.writeFileSync(inputFile, source);
        } else if (fs.existsSync(outputFile)) {
            return outputFile;
        }

        return this.writeRenderedFile(inputFile, outputFile, type);
    }

    private async imageProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext, type: RenderType): Promise<void> {
        try {
            console.debug(`Call image processor for ${type}`);

            const imagePath = await this.convertToImage(type, source.trim());

            //const img = document.createElement('img');
            //img.src = `app://local${imagePath}`;
            el.classList.add('multi-graph-normal');
            el.innerHTML = fs.readFileSync(imagePath).toString();
            //el.appendChild(img);
        } catch (errMessage) {
            console.error('convert to image error: ' + errMessage);
            const pre = document.createElement('pre');
            const code = document.createElement('code');
            code.setText(errMessage);
            pre.appendChild(code);
            el.appendChild(pre);
        }
    }

    public async refgraphProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext): Promise<void> {
        return this.imageProcessor(source, el, _, 'refgraph');
    }

    public async blockdiagProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext): Promise<void> {
        return this.imageProcessor(source, el, _, 'blockdiag');
    }

    public async ditaaProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext): Promise<void> {
        return this.imageProcessor(source, el, _, 'ditaa');
    }

    public async dotProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext): Promise<void> {
        return this.imageProcessor(source, el, _, 'dot');
    }

    public async latexProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext): Promise<void> {
        return this.imageProcessor(source, el, _, 'latex');
    }

}
