@ECHO OFF

::----------------------------------------------------------------------
:: Android Studio startup script.
::----------------------------------------------------------------------

:: ---------------------------------------------------------------------
:: Ensure IDE_HOME points to the directory where the IDE is installed.
:: ---------------------------------------------------------------------
SET IDE_BIN_DIR=%~dp0
FOR /F "delims=" %%i in ("%IDE_BIN_DIR%\..") DO SET IDE_HOME=%%~fi

:: ---------------------------------------------------------------------
:: Locate a JRE installation directory which will be used to run the IDE.
:: Try (in order): STUDIO_JDK, studio%BITS%.exe.jdk, ..\jbr[-x86], JDK_HOME, JAVA_HOME.
:: ---------------------------------------------------------------------
SET JRE=

IF NOT "%STUDIO_JDK%" == "" (
  IF EXIST "%STUDIO_JDK%" SET JRE=%STUDIO_JDK%
)

SET BITS=64
SET _USER_JRE64_FILE=%APPDATA%\Google\AndroidStudioPreview2021.1\studio%BITS%.exe.jdk
SET BITS=
SET _USER_JRE_FILE=%APPDATA%\Google\AndroidStudioPreview2021.1\studio%BITS%.exe.jdk
IF "%JRE%" == "" (
  SET _JRE_CANDIDATE=
  IF EXIST "%_USER_JRE64_FILE%" (
    SET /P _JRE_CANDIDATE=<"%_USER_JRE64_FILE%"
  ) ELSE IF EXIST "%_USER_JRE_FILE%" (
    SET /P _JRE_CANDIDATE=<"%_USER_JRE_FILE%"
  )
)
IF "%JRE%" == "" (
  IF NOT "%_JRE_CANDIDATE%" == "" IF EXIST "%_JRE_CANDIDATE%" SET JRE=%_JRE_CANDIDATE%
)

IF "%JRE%" == "" (
  IF "%PROCESSOR_ARCHITECTURE%" == "AMD64" IF EXIST "%IDE_HOME%\jbr" SET JRE=%IDE_HOME%\jbr
)
IF "%JRE%" == "" (
  IF EXIST "%IDE_HOME%\jbr-x86" SET JRE=%IDE_HOME%\jbr-x86
)

IF "%JRE%" == "" (
  IF EXIST "%JDK_HOME%" (
    SET JRE=%JDK_HOME%
  ) ELSE IF EXIST "%JAVA_HOME%" (
    SET JRE=%JAVA_HOME%
  )
)

SET JAVA_EXE=%JRE%\bin\java.exe
IF NOT EXIST "%JAVA_EXE%" (
  ECHO ERROR: cannot start Android Studio.
  ECHO No JRE found. Please make sure STUDIO_JDK, JDK_HOME, or JAVA_HOME point to a valid JRE installation.
  EXIT /B
)

SET BITS=
FINDSTR /B /C:"OS_ARCH=\"x86_64\"" "%JRE%\release" > NUL
IF NOT ERRORLEVEL 1 SET BITS=64
FINDSTR /B /C:"OS_ARCH=\"amd64\"" "%JRE%\release" > NUL
IF NOT ERRORLEVEL 1 SET BITS=64

:: ---------------------------------------------------------------------
:: Collect JVM options and properties.
:: ---------------------------------------------------------------------
IF NOT "%STUDIO_PROPERTIES%" == "" SET IDE_PROPERTIES_PROPERTY="-Didea.properties.file=%STUDIO_PROPERTIES%"

SET VM_OPTIONS_FILE=
SET USER_VM_OPTIONS_FILE=
IF NOT "%STUDIO_VM_OPTIONS%" == "" (
  :: 1. %<IDE_NAME>_VM_OPTIONS%
  IF EXIST "%STUDIO_VM_OPTIONS%" SET VM_OPTIONS_FILE=%STUDIO_VM_OPTIONS%
)
IF "%VM_OPTIONS_FILE%" == "" (
  :: 2. <IDE_HOME>.vmoptions || <IDE_HOME>\bin\<exe_name>.vmoptions + <IDE_HOME>.vmoptions (Toolbox)
  IF EXIST "%IDE_HOME%.vmoptions" (
    SET VM_OPTIONS_FILE=%IDE_HOME%.vmoptions
    FINDSTR /B /C:"-ea" "%IDE_HOME%.vmoptions" > NUL
    IF ERRORLEVEL 1 IF EXIST "%IDE_BIN_DIR%\studio%BITS%.exe.vmoptions" (
      :: partial - prepend with default options
      SET VM_OPTIONS_FILE=%IDE_BIN_DIR%\studio%BITS%.exe.vmoptions
      SET USER_VM_OPTIONS_FILE=%IDE_HOME%.vmoptions
    )
  )
)
IF "%VM_OPTIONS_FILE%" == "" (
  :: 3. <config_directory>\<exe_name>.vmoptions
  IF EXIST "%APPDATA%\Google\AndroidStudioPreview2021.1\studio%BITS%.exe.vmoptions" (
    SET VM_OPTIONS_FILE=%APPDATA%\Google\AndroidStudioPreview2021.1\studio%BITS%.exe.vmoptions
  )
)
IF "%VM_OPTIONS_FILE%" == "" (
  :: 4. <IDE_HOME>\bin\[win\]<exe_name>.vmoptions [+ <config_directory>\user.vmoptions]
  IF EXIST "%IDE_BIN_DIR%\studio%BITS%.exe.vmoptions" (
    SET VM_OPTIONS_FILE=%IDE_BIN_DIR%\studio%BITS%.exe.vmoptions
  ) ELSE IF EXIST "%IDE_BIN_DIR%\win\studio%BITS%.exe.vmoptions" (
    SET VM_OPTIONS_FILE=%IDE_BIN_DIR%\win\studio%BITS%.exe.vmoptions
  )
  IF EXIST "%APPDATA%\Google\AndroidStudioPreview2021.1\user.vmoptions" (
    SET USER_VM_OPTIONS_FILE=%APPDATA%\Google\AndroidStudioPreview2021.1\user.vmoptions
  )
)

SET ACC=
IF "%VM_OPTIONS_FILE%" == "" IF NOT "%USER_VM_OPTIONS_FILE%" == "" (
  SET VM_OPTIONS_FILE=%USER_VM_OPTIONS_FILE%
  SET USER_VM_OPTIONS_FILE=
)
IF "%VM_OPTIONS_FILE%" == "" (
  ECHO ERROR: cannot find VM options file.
) ELSE (
  IF NOT "%USER_VM_OPTIONS_FILE%" == "" (
    SET ACC=-Djb.vmOptionsFile="%USER_VM_OPTIONS_FILE%"
  ) ELSE (
    SET ACC=-Djb.vmOptionsFile="%VM_OPTIONS_FILE%"
  )
  FOR /F "eol=# usebackq delims=" %%i IN ("%VM_OPTIONS_FILE%") DO CALL "%IDE_BIN_DIR%\append.bat" "%%i"
  IF NOT "%USER_VM_OPTIONS_FILE%" == "" (
    FOR /F "eol=# usebackq delims=" %%i IN ("%USER_VM_OPTIONS_FILE%") DO CALL "%IDE_BIN_DIR%\append.bat" "%%i"
  )
)

SET CLASS_PATH=%IDE_HOME%\lib\bootstrap.jar
SET CLASS_PATH=%CLASS_PATH%;%IDE_HOME%\lib\util.jar
SET CLASS_PATH=%CLASS_PATH%;%IDE_HOME%\lib\jdom.jar
SET CLASS_PATH=%CLASS_PATH%;%IDE_HOME%\lib\log4j.jar
SET CLASS_PATH=%CLASS_PATH%;%IDE_HOME%\lib\jna.jar
SET CLASS_PATH=%CLASS_PATH%;%JDK%\lib\tools.jar
IF NOT "%STUDIO_CLASS_PATH%" == "" SET CLASS_PATH=%CLASS_PATH%;%STUDIO_CLASS_PATH%

:: ---------------------------------------------------------------------
:: Run the IDE.
:: ---------------------------------------------------------------------
"%JAVA_EXE%" ^
  -cp "%CLASS_PATH%" ^
  %ACC% ^
  "-XX:ErrorFile=%USERPROFILE%\java_error_in_studio_%%p.log" ^
  "-XX:HeapDumpPath=%USERPROFILE%\java_error_in_studio.hprof" ^
  -Didea.vendor.name=Google -Didea.paths.selector=AndroidStudioPreview2021.1 ^
  -Didea.platform.prefix=AndroidStudio -Didea.jre.check=true ^
  %IDE_PROPERTIES_PROPERTY% ^
  com.intellij.idea.Main ^
  %*
