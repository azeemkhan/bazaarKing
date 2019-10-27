#!/bin/bash

#-------------------------------------------------------
#  This Script will create MYSQL db user and database 
#------------------------------------------------------
# VARIABLES
#
_reset=$(tput sgr0)

_red=$(tput setaf 1)
_green=$(tput setaf 76)

function _arrow()
{
    printf "➜ $@\n"
}

function _success()
{
    printf "${_green}✔ %s${_reset}\n" "$@"
}

function _error() {
    printf "${_red}✖ %s${_reset}\n" "$@"
}

function createMysqlDbUser()
{

    if [ -f /root/.my.cnf ]; then
        $BIN_MYSQL -e "GRANT ALL PRIVILEGES ON *.* TO ${DB_USER}@${DB_HOST} IDENTIFIED BY '${DB_PASS}';"
        $BIN_MYSQL -u${DB_USER} -p${DB_PASS} -e "DROP DATABASE IF EXISTS ${DB_NAME};"
        $BIN_MYSQL -u${DB_USER} -p${DB_PASS} -e "CREATE DATABASE ${DB_NAME};"
    else
        # If /root/.my.cnf doesn't exist then it'll ask for root password
        _arrow "Please enter root user MySQL password!"
        $BIN_MYSQL -uroot -p -e "GRANT ALL PRIVILEGES ON *.* TO ${DB_USER}@${DB_HOST} IDENTIFIED BY '${DB_PASS}';"
        $BIN_MYSQL -u${DB_USER} -p${DB_PASS} -e "DROP DATABASE IF EXISTS ${DB_NAME};"
        $BIN_MYSQL -u${DB_USER} -p${DB_PASS} -e "CREATE DATABASE ${DB_NAME};"
    fi
}

function printSuccessMessage()
{
    _success "MySQL DB / User creation completed!"

    echo "################################################################"
    echo ""
    echo " >> Host      : ${DB_HOST}"
    echo " >> Database  : ${DB_NAME}"
    echo " >> User      : ${DB_USER}"
    echo " >> Password  : ${DB_PASS}"
    echo ""

}

################################################################################
# Main
################################################################################

DIR="$( cd "$( dirname "$0" )" && pwd )"
BIN_MYSQL=$(which mysql)

DB_HOST=
DB_NAME=
DB_USER=
DB_PASS=

function main()
{
    _success "Creating MySQL db and user..."
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
    
    createMysqlDbUser
    if [ "$?" -ne "0" ]; then
	_error "Something wrong.Could not create MYSQL User"
	exit 1
    else
    	_success "Done!"
    	printSuccessMessage
    fi

    exit 0
}

main "$@"

