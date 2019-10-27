
## Install MySQL 5.7 on macOS

### Install MySQL
* Enter the command : `$ brew install mysql@5.7`
* Install **brew services** first : `$ brew tap homebrew/services`
* Load and start the MySQL service : `$ brew services start mysql@5.7`.   
Expected output : **Successfully started `mysql` (label: homebrew.mxcl.mysql)** 	  
* Check of the MySQL service has been loaded : `$ brew services list` <sup>[1](#1)</sup>
* Force link 5.7 version - `$ brew link mysql@5.7 --force` 
* Verify the installed MySQL instance : `$ mysql -V`.   
Expected output : **Ver 14.14 Distrib 5.7.22, for osx10.13 (x86_64)**  

### Set Password
Open Terminal and execute the following command to set the root password:  
 `mysqladmin -u root password 'yourpassword'`  
 
### Database Management
To manage your databases use [Sequel Pro](http://www.sequelpro.com).   

### Create User And DataBase
* Goto db/scripts directory
* Run the scripts : `$./credb.sh`
### Create the tables
* Goto db/scripts dorectory
* Run the scripts : `$./setupdb.sh`
