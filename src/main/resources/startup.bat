@echo off
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' (
goto UACPrompt
) else ( goto gotAdmin )
:UACPrompt
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
exit /B
:gotAdmin
if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
pushd "%CD%"
CD /D "%~dp0"


set FolderName=%~dp0

set APP_NAME=quickrun-1.0-SNAPSHOT.jar

for /f "delims=\" %%a in ('dir /b /a-d /o-d "%FolderName%\*.*"') do (
	set APP_NAME=%%a
)


set start_up_reg=HKEY_LOCAL_MACHINE\Software\Microsoft\Windows\CurrentVersion\Run

REG QUERY %start_up_reg% /s|find "quickrun"

 

IF ERRORLEVEL 1 (

  echo %start_up_reg% not found.
  reg add %start_up_reg% /v quickrun /t reg_sz /d "\"%~dp0%APP_NAME%\""

) ELSE (

  echo %start_up_reg% found!
	reg delete %start_up_reg% /v quickrun /f
)
