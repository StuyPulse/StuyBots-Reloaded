#!/bin/bash

LOG_DIRECTORY=".logs"
LOG_EXTENSION="build.log"

BUILD_SUCCESS="Build Successful!"
BUILD_ERROR="Errors While Building! (Check \"$LOG_DIRECTORY\")"
GRADLE_MISSING="Missing Gradle Wrapper + Missing Gradle Executable!"
GRADLE_ERROR="Missing Gradle Wrapper + Issue Generating Wrapper!"

build_results=()

function log_and_build {
    # If no log directory exists, create one
    if [ ! -d "../$LOG_DIRECTORY" ]; then 
        mkdir -p "../$LOG_DIRECTORY"
    fi

    # Location of log file
    log_file="../$LOG_DIRECTORY/$1.$LOG_EXTENSION"

    # If log file exists, remove it
    if [ -e $log_file ]; then 
        rm $log_file
    fi

    # Create log file and write to it
    touch $log_file
    ./gradlew build &> $log_file
}

function test_build {
    # CD into robot project
    cd "./$1"

    # Padding
    echo ""

    # Check to see if folder is valid robot project
    if [ -e "./build.gradle" ] && [ -e "./settings.gradle" ]; then
        echo "Running Test Build For \"$1\" To Check For Errors:"

        # Test to see if there is gradle wrapper file
        if [ ! -e "./gradlew" ]; then 
            echo " - Unable to find Gradle Wrapper!"
            
            # Check to see if we can generate gradle wrapper file
            if [ -x "$(command -v gradle)" ]; then
                echo " - Generating Gradle Wrapper..."
                gradle wrapper &> /dev/null

                # See if gradle wrapper file was created successfully
                if [ -e "./gradlew" ]; then 
                    echo " - Generated Gradle Wrapper Succesfully!"
                else
                    echo " - Error generating Gradle Wrapper!"
                    echo " - Aborting robot project \"$1\""
                    build_results+=("$1: $GRADLE_ERROR")
                    return -1
                fi
            else
                echo " - Unable to find Gradle executable to generate Gradle Wrapper!"
                echo " - Aborting robot project \"$1\""
                build_results+=("$1: $GRADLE_MISSING")
                return -1
            fi
        fi

        # Build robot project and log its output
        echo " - Building robot project..."
        log_and_build $1

        # Check to see if robot project was built successfully
        if [ $? == 0 ]; then 
            echo " - Robot project built successfully!"
            build_results+=("$1: $BUILD_SUCCESS")
        else
            echo " - Robot project DID NOT build successfully!"
            echo " - Check \"$LOG_DIRECTORY\" to see what went wrong!"
            build_results+=("$1: $BUILD_ERROR")
        fi
    else
        echo "Skiping Folder \"$1\" as it is not a robot project!"
    fi
    cd "../"
}

# Go through folders and test build them
for project in */; do
    # Remove "/" from end of project name   
    test_build ${project::-1}
done

# Write a summary of the builds
echo ""
echo ""
echo "Summary of Builds:"
for result in "${build_results[@]}"; do
    echo " - $result"
done