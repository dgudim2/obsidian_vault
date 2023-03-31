import { PluginSettingTab, Setting } from 'obsidian';
import GraphvizPlugin from './main';

export interface GraphvizSettings {
  dotPath: string;
  pdflatexPath: string;
  imageMagickConvertPath: string;
  latexRenderBg: string
}

export const DEFAULT_SETTINGS: GraphvizSettings = {
  dotPath: 'dot',
  pdflatexPath: 'pdflatex',
  imageMagickConvertPath: 'convert',
  latexRenderBg: '#ebdbb2'
};

export class GraphvizSettingsTab extends PluginSettingTab {
  plugin: GraphvizPlugin;

  constructor(plugin: GraphvizPlugin) {
    super(plugin.app, plugin);
    this.plugin = plugin;
  }

  display(): void {
    const {containerEl} = this;

    containerEl.empty();

    new Setting(containerEl).setName('Latex rendering background')
    .setDesc('Background of the rendered latex codeblocks')
    .addText(text => text.setPlaceholder(DEFAULT_SETTINGS.imageMagickConvertPath)
      .setValue(this.plugin.settings.imageMagickConvertPath)
      .onChange(async (value) => {
          this.plugin.settings.imageMagickConvertPath = value;
          await this.plugin.saveSettings();
        }
      )
    );

    new Setting(containerEl).setName('ImageMagick convert Path')
      .setDesc('ImageMagick convert executable path')
      .addText(text => text.setPlaceholder(DEFAULT_SETTINGS.imageMagickConvertPath)
        .setValue(this.plugin.settings.imageMagickConvertPath)
        .onChange(async (value) => {
            this.plugin.settings.imageMagickConvertPath = value;
            await this.plugin.saveSettings();
          }
        )
      );

    new Setting(containerEl).setName('Pdflatex Path')
      .setDesc('Pdflatex executable path')
      .addText(text => text.setPlaceholder(DEFAULT_SETTINGS.pdflatexPath)
        .setValue(this.plugin.settings.pdflatexPath)
        .onChange(async (value) => {
            this.plugin.settings.pdflatexPath = value;
            await this.plugin.saveSettings();
          }
        )
      );

    new Setting(containerEl).setName('Dot Path')
      .setDesc('Dot executable path')
      .addText(text => text.setPlaceholder(DEFAULT_SETTINGS.dotPath)
        .setValue(this.plugin.settings.dotPath)
        .onChange(async (value) => {
            this.plugin.settings.dotPath = value;
            await this.plugin.saveSettings();
          }
        )
      );

  }
}
