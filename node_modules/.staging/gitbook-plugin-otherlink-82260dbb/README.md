# Link to non-md files under sub-directories

> When link to files under sub-directories, you can use other types of link than md file link, and open it in a new window.

## Unsupported cases

in file `SUMMARY.md`:

    * [title A](path/to/file-a.md)
    * [title B](path/to/file-a.html)
    * [title C](path/to/file-a.pdf)

gitbook must parse the linked file in `SUMMARY.md`, so `*.html`, `*.pdf` files will fail the process.

And in file `file-a.md`:

    * [title A](path/to/file-a.md)
    * [title B](path/to/file-a.html)
    * [title C](path/to/file-a.pdf)

the relative link will be opened in the same window. The file to be opened must get by ajax and then parsed in the browser in a specific way, so `*.html` and `*.pdf` link also fail to open.

    
## Usage

in `book.json`:

    {
        plugins: [ "otherlink" ]
    }

in `.bookignore`:

    *.html
    *.pdf

to exclude the file types which you may use in SUMMARY.md.

and `in your md files`, write links of sub-directories' files in the below format:

    * [title B](ref://path/to/file-a.html)
    * [title C](ref://path/to/file-a.pdf)

just prefix the link `ref://`.




