
#!/bin/bash

#-------------------------------------------------------
#  This Script will create all required tables 
#------------------------------------------------------
# VARIABLES
#
_reset=$(tput sgr0)

_red=$(tput setaf 1)
_green=$(tput setaf 76)

function _success()
{
    printf "${_green}✔ %s${_reset}\n" "$@"
}

function _error() {
    printf "${_red}✖ %s${_reset}\n" "$@"
}

function createTables()
{
    for file in `ls -1 ${DDL_DIR}`
    do
	_success "Creating table [$file]..."
	$BIN_MYSQL -u${DB_USER} -p${DB_PASS} -D ${DB_NAME} < "${DDL_DIR}/${file}";
        if [ "$?" -ne "0" ]; then
        	_error "Something wrong.Could not create table $file"
        	exit 1
	else
		_success "Done."
    	fi
   done;
}

################################################################################
# Main
################################################################################

DIR="$( cd "$( dirname "$0" )" && pwd )"
DDL_DIR="${DIR}/../mysql/ddls"
BIN_MYSQL=$(which mysql)

DB_HOST=
DB_NAME=
DB_USER=
DB_PASS=
function main()
{
    if [ -f "${DIR}/db.conf" ]; then 
	source "${DIR}/db.conf"
    	DB_HOST=${db_server}
    	DB_USER=${db_user}
    	DB_NAME=${db_name}
    	DB_PASS=${db_password}
    else
	_error "${DIR}/db.conf is missing."
	exit 1
    fi	
    
    createTables
    if [ "$?" -ne "0" ]; then
	_error "Something wrong.Could not create MYSQL User"
	exit 1
    else
    	_success "All tables created successfully!!!"
    fi
    exit 0
}

main "$@"

