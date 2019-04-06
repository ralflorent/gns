## GNS - Web Application

**GNS UI** is the HTTP web client module for the GNS project, used to access geospatial data from the GNS API and take notes.

This web application, known as [GNS Web App](http://www.gns.local), is used to manage different tasks when it comes to taking notes and archiving them. Among others, certain tasks feature the following: locating the current position of the device (combo: Raspberry PI + GPS puck); adding, editing, and/or viewing existing notes; creating base maps based on the path traveled during the process of note taking, and so forth. To build the website from scratch, follow the instructions below from a development standpoint up to production.

---

### Getting Started
Assuming that the reader has the basic programming/DevOps knowledge, this document aims to explain the procedure implemented to build the web application up to deploying it on the Apache web server.

#### Prerequisites
This web interface module is built with [Angular Framework v7.2.1](https://angular.io), powered by Google, Inc.

If not yet installed, please install these following task runners using a preferred command line console *(linux terminal, Git Bash, Windows Powershell, Iterm2, etc.)*:

1. Download and install [Node and npm](https://nodejs.org/en/download/)
2. Once node and npm installed, this [reference](https://yarnpkg.com/lang/en/docs/install/) can be used, or run the command `npm install -g yarn` to install **Yarn** globally
3. Install **Angular CLI** by running this command `npm install -g @angular/cli` or using this [reference](https://cli.angular.io/).

In case of errors during the installation, one can always google-search for quick fixes or use the references (mentioned above) of the respective official web pages for more information on those errors.

If everything goes well, the next step is to proceed with setting up a development environment (dev-env) so the app can be tested and run locally. But if the user is not interested in visualizing the app in his/her local machine, s/he can always skip this step and go directly to the deployment section.

---
### Run the app locally
To run the web page, the developer should set up a dev-env. To do so, s/he need an IDE such as IntelliJ IDEA, Visual Studio Code, WebStorm, and so on. His/her preference is what really matters.
Since the web page is an [Angular](https://angular.io/) web based app, Visual Studio Code seems to work quite well with it and contains some useful features at the time of developing. Other consideration could be IntelliJ IDEA as a strong, stable IDE for development.

PS: *More details will be provided soon on how to configure an angular-adapted environment for a specific IDE*.

Let's finally run the app:

1. Clone (or download) the repository [here](https://github.com/ralflorent/gns.git) using any CLI or any preferred `Application`.
2. Checkout to the `develop` branch and pull the changes in case of any updates
3. Open the project using an IDE (VS Code, for example).
4. Locate the **Terminal** of the IDE, make sure it's open in the current working directory (which is `gns-ui`).
5. Run the following command `yarn`. Being installed globally, **Yarn** will scan the *package.json* file and install all the dependencies accordingly. (This might take a little while)
6. After installing the dependencies, the developer can run the following command `yarn start` to run the app locally. The Angular CLI will be in charge and open the web app in the default web browser.

Notes: *In order to simulate a production-like environment while at the development environment, some changes might be necessary in the package.json file:*

* *The default port used by Angular CLI is 4200. To avoid conflicts with existing apps running in port 4200, set the following starting script `ng serve --port [port_number]` in the `package.json` file.*
* *GNS Wep App requires the GNS API, which is an independent service located in another port, to load GPS data, and perform specific tasks.*

Finally, running  the command `yarn start` in the selected Terminal should start the app.

### Deploy the app to the web server
The web page, at the time of writing this documentation, is at its `v1.0.1` version. This section covers specific headache at the moment of running and deploying the web application on the Apache server of the Raspberry PI.

1. First off, make sure the Apache server is up and running (Read more here on how to set and run Apache2 on Raspberry PI)
2. Build the GNS Web app for production by running `yarn run build` command in the CLI. Once built successfully, the GNS UI is ready to be deployed (go to production)

    Notes: *When built with Angular, using `ng build --prod` will generate hashed files under a `gns-ui`.*

3. Zip the folder `gns-ui/dist/gns-ui/*` to `gns-ui.zip`
4. Transfer the `gns-ui.zip` via a client (S)FTP (it is recommended the use of [Filezilla](https://filezilla-project.org/) to avoid complex client-side configuration), or via SCP over ssh connection.

    Notes: *Copy the zip file `gns-ui.zip` to the Raspberry PI using `scp` or `sftp` via a terminal. For example, using the PI as hotspot: `scp gns-ui.zip pi@10.10.10.11:~/Projects`*

5. Connect to the PI using `ssh` via a terminal. For example, using the PI as hotspot: `ssh -l pi 10.10.10.11`

6. Copy and unzip the zip file `gns-ui.zip` to `/var/www/html`

7. Enable rewriting module for Apache2 web server (Read more about it [here](https://www.digitalocean.com/community/tutorials/how-to-rewrite-urls-with-mod_rewrite-for-apache-on-ubuntu-16-04))
8. Finally, go the browser, try to load and reload the page.

*PS: If it runs on the local machine, it should run here on the web server too. Always make sure the tests are passed. But if any errors, the developer can always check the console output.*

### Happy Coding!