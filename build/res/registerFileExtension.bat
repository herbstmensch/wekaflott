@echo off

IF "%PROCESSOR_ARCHITECTURE%"=="x86" (set bit=) else (set bit=64)
set app=%~dp0WeKaFlott%bit%.exe

Assoc *.wkt=wktFile
Ftype wktFile="%app%" "%%1"
