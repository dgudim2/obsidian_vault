import { MarkdownPostProcessorContext } from 'obsidian';
import * as os from 'os';
import * as path from 'path';
import * as fs from 'fs';
import { spawn } from 'child_process';
import GraphvizPlugin from './main';

import { GraphvizSettings } from './setting';

import * as crypto from 'crypto';
const md5 = (contents: string) => crypto.createHash('md5').update(contents).digest('hex');

type RenderType = 'dot' | 'latex' | 'ditaa' | 'blockdiag';

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

    ['yellow', '--g-color-light-yellow'],
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

    constructor(plugin: GraphvizPlugin) {
        this.pluginSettings = plugin.settings;
        this.renderTypeMapping = new Map<RenderType, (source: string, el: HTMLElement, ctx: MarkdownPostProcessorContext) => Promise<unknown> | void>([
            ['latex', this.latexProcessor],
            ['dot', this.dotProcessor],
            ['ditaa', this.ditaaProcessor]
        ]);
    }

    private getRendererParameters(type: RenderType, sourceFile: string): [string, string, string[]] {
        const outputFile = `${sourceFile}.svg`;
        switch (type) {
            case 'dot':
                return [this.pluginSettings.dotPath, outputFile, ['-Tsvg', sourceFile, '-o', outputFile]];
            case 'latex':
                return [this.pluginSettings.pdflatexPath, outputFile, ['-shell-escape', '-output-directory', this.getTempDir(type), sourceFile]];
            case 'ditaa':
                return [this.pluginSettings.ditaaPath, outputFile, [sourceFile, '--transparent', '--svg', '--overwrite']];
            case 'blockdiag':
                return [this.pluginSettings.ditaaPath, outputFile, ['--antialias', '-Tsvg', sourceFile, '-o', outputFile]];
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

    private async writeRenderedFile(sourceFile: string, type: RenderType): Promise<string> {

        const [cmdPath, outputFile, params] = this.getRendererParameters(type, sourceFile);

        if (fs.existsSync(outputFile)) {
            return outputFile;
        }

        await this.spawnProcess(cmdPath, params);
        if (type === 'latex') {
            await this.spawnProcess(this.pluginSettings.pdf2svgPath, [`${sourceFile}.pdf`, outputFile, '0']);
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

    private async convertToImage(type: RenderType, source: string): Promise<string> {

        const dir = this.getTempDir(type);
        const file = path.join(dir, md5(source));

        if (!fs.existsSync(dir)) {
            fs.mkdirSync(dir);
        }

        if (!fs.existsSync(file)) {
            fs.writeFileSync(file, source);
        }
        return this.writeRenderedFile(file, type);
    }

    private async imageProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext, type: RenderType): Promise<void> {
        try {
            console.debug(`Call image processor for ${type}`);

            if (type === 'dot' && source.trimEnd().at(-1) != '}') {
                console.error('Bad source, won\'t render');
                return;
            }

            const imagePath = await this.convertToImage(type, source);

            //const img = document.createElement('img');
            //img.src = `app://local${imagePath}`;
            el.classList.add('multi-graph-normal');
            el.innerHTML = fs.readFileSync(imagePath).toString();
            //el.appendChild(img);
        } catch (errMessage) {
            console.error('convert to image error: ' + errMessage);
            const pre = document.createElement('pre');
            const code = document.createElement('code');
            pre.appendChild(code);
            code.setText(errMessage);
            el.appendChild(pre);
        }
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
