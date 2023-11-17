# Remote Coding Environments For Gaffer

Gaffer is now configured for remote coding environemnts such as GitHub
Codespaces and Gitpod. This addition allows for an easier and faster way to
contribute to Gaffer with no manual setup or need to download dependencies to a
local machine.

## GitHub Codespaces
To use GitHub Codespaces simply open the Gaffer repository, click the "Code"
button drop down, and then the option labeled "Create codespace on develop".

This will launch a Codespaces environment with all the configuration needed to
contribute to Gaffer. See the [GitHub documentation for more information on
Codespaces](https://github.com/features/codespaces).

## Gitpod
To use Gitpod you can simply prefix any GitHub URL with **`gitpod.io/#`** and
follow the steps from there. You can also install the
[extension](https://www.gitpod.io/docs/configure/user-settings/browser-extension)
on your web browser. This adds a button to GitHub that does the prefixing for
you.

Our custom GitPod configuration removes your Git commit email so you
will need to [re-configure your Git commit
email](https://www.gitpod.io/docs/configure/authentication/github). You can also
[configure your Git commit email to be a private GitHub email or a custom email
too](https://www.gitpod.io/docs/configure/authentication#how-to-use-a-private-github-email-or-custom-email-for-git-commits).
Once done your environment will be all set to contribute to the Gaffer
repository.

See the [Gitpod documentation for more
information](https://www.gitpod.io/docs/introduction).