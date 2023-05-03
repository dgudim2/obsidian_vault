# Index

``` dataviewjs
const folderlist = ['Computer architecture', 'Discrete math', 'Presentations']

for (let folder of folderlist) {
    dv.header(2, folder);
	for (let group of dv.pages(`"uni/${folder}"`).groupBy(p => p.file.folder)) {
		const header = group.key.substring(4);
		if(header.contains('/')) {
			dv.header(3, header.substring(header.lastIndexOf('/') + 1));
		}
		dv.table(["Name ", "Modified"],
		group.rows.map(k => [k.file.link, k.file.mtime]))
	}
}
```
