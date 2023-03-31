import { MarkdownPostProcessorContext } from 'obsidian';
import * as os from 'os';
import * as path from 'path';
import * as fs from 'fs';
import { spawn } from 'child_process';
import GraphvizPlugin from './main';

import * as crypto from 'crypto';
const md5 = (contents: string) => crypto.createHash('md5').update(contents).digest('hex');

type RenderType = 'dot' | 'latex';



export class Processors {
    plugin: GraphvizPlugin;

    constructor(plugin: GraphvizPlugin) {
        this.plugin = plugin;
    }

    private getRenderer(type: RenderType): string {
        switch (type) {
            case 'dot':
                return this.plugin.settings.dotPath;
            case 'latex':
                return this.plugin.settings.pdflatexPath;
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
                resolve('');
            });
        });
    }

    private async writeRenderedFile(sourceFile: string, type: RenderType): Promise<string> {

        const cmdPath = this.getRenderer(type);
        const out = `${sourceFile}.png`;

        if (fs.existsSync(out)) {
            return out;
        }

        const parameters = type === 'dot' ? ['-Tpng', sourceFile, '-o', out] : ['-shell-escape', '-output-directory', this.getTempDir(type), sourceFile];

        await this.spawnProcess(cmdPath, parameters);
        if (type === 'latex') {
            await this.spawnProcess(this.plugin.settings.imageMagickConvertPath, ['-density', '300', '-units', 'PixelsPerInch', `${sourceFile}.pdf[0]`, out]);
        }

        return out;
    }

    private getTempDir(type: RenderType): string {
        return path.join(os.tmpdir(), `obsidian-${type}`);
    }

    private async convertToImage(source: string, type: RenderType): Promise<string> {

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

    public async dotProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext): Promise<void> {
        return this.imageProcessor(source, el, _, 'dot');
    }

    public async latexProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext): Promise<void> {
        return this.imageProcessor(source, el, _, 'latex');
    }

    private async imageProcessor(source: string, el: HTMLElement, _: MarkdownPostProcessorContext, type: RenderType): Promise<void> {
        try {
            console.debug(`Call image processor for ${type}`);

            if (type === 'dot' && source.trimEnd().at(-1) != '}') {
                console.error('Bad source, won\'t render');
                return;
            }

            const imagePath = await this.convertToImage(source, type);
            const img = document.createElement('img');
            img.src = `app://local${imagePath}`;
            el.appendChild(img);
        } catch (errMessage) {
            console.error('convert to image error: ' + errMessage);
            const pre = document.createElement('pre');
            const code = document.createElement('code');
            pre.appendChild(code);
            code.setText(errMessage);
            el.appendChild(pre);
        }
    }
}
