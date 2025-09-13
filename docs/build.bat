@ECHO OFF

CLS

SETLOCAL ENABLEDELAYEDEXPANSION

ECHO ~~~ SET JAVA VARIABLES ~~~
SET JAVA_HOME=C:\jdk-21
SET PATH=%JAVA_HOME%\bin;%PATH%

ECHO ~~~ SET JAVAFX VARIABLES ~~~
SET JAVAFX_HOME=C:\javafx-sdk-21
SET JAVAFX_MODULES=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media
SET JAVAFX_ARGS=--module-path "%JAVAFX_HOME%\lib" --add-modules=%JAVAFX_MODULES%

ECHO ~~~ SET VARIABLES ~~~
SET docs=..\docs
SET src=..\src
SET bin=..\bin
SET javaDoc=%bin%\JavaDocs
SET data=..\data

ECHO ~~~ CLEAN ~~~
IF EXIST %bin% (
  DEL /S /Q "%bin%\*" > nul 2>&1
  RMDIR /S /Q %bin% > nul 2>&1
)
MKDIR %bin%
IF EXIST %javaDoc% (
  DEL /S /Q "%javaDoc%\*" > nul 2>&1
  RMDIR /S /Q %javaDoc% > nul 2>&1
)
MKDIR %javaDoc%

:COMPILE
javac %JAVAFX_ARGS% -d %bin% ^
  "%src%\Main.java" ^
  "%src%\legecy\panda\Server.java" ^
  "%src%\legecy\panda\Display.java" ^
  "%src%\legecy\panda\Client.java"  
if errorLevel 1 (
  echo "Failed Compilation"
  pause
  exit /b 1
)

:JAVADOC
javadoc %JAVAFX_ARGS% -sourcepath "%src%" -d "%javaDoc%" -subpackages legecy.panda
if errorLevel 1 (
  echo "Failed JavaDoc"
  pause 
  exit /b 1
)

:RUN
java %JAVAFX_ARGS% -cp "%bin%" Main
if errorLevel 1 (
  echo "Failed running"
  pause
  exit /b 1
)

CD %docs%

ECHO ~~~ END ~~~
PAUSE