# check_services.py
# Automation script for checking if the required services are running

import os

# The required services this script will attempt to run in order
# place services in order of dependencies
# preceeding services will NOT start until the previous one has been started
services = ['mariadb', 'gpsd', 'gns', 'apache2']

# The number of retries for running services before ending execution with an error
NUM_TRIES = 5

# Success status code from systemctl
SERVICE_RUNNING_CODE = 0


def check_services():
    """ Checks if the required services are running and will attempt to run if they are not """
    servicesNotRunning = []
    for service in services:
        status = os.system("service " + service + " status >/dev/null 2>&1")
        if status != SERVICE_RUNNING_CODE:
            print("Service", service, "is not running.")
            servicesNotRunning.append(service)
        else:
            print("Service", service, "is running.")
    
    if len(servicesNotRunning) > 0:
        run_services(servicesNotRunning)


def run_services(servicesToRun):
    """ Attempts to run services that are not already running """

    shouldRetry = True
    currentTries = 0

    while (shouldRetry == True and currentTries < NUM_TRIES):
        for service in servicesToRun:
            status = os.system("sudo systemctl start " + service)
            while (status != SERVICE_RUNNING_CODE) and (currentTries < NUM_TRIES):
                shouldRetry = True
                currentTries += 1
                status = os.system("sudo systemctl start " + service)
            servicesToRun.remove(service)
            shouldRetry = False


check_services()
