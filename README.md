# SonarQube plugin for Groovy

[![Build status (Travis CI)](https://travis-ci.com/Inform-Software/sonar-groovy.svg?branch=master)](https://travis-ci.com/Inform-Software/sonar-groovy)
[![Build status (AppVeyor)](https://ci.appveyor.com/api/projects/status/si4v2gs7h4qiv27j/branch/master?svg=true)](https://ci.appveyor.com/project/TobiX/sonar-groovy/branch/master)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.sonarsource.groovy%3Agroovy&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.sonarsource.groovy%3Agroovy)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.sonarsource.groovy%3Agroovy&metric=coverage)](https://sonarcloud.io/dashboard?id=org.sonarsource.groovy%3Agroovy)

Get test builds from [AppVeyor](https://ci.appveyor.com/project/TobiX/sonar-groovy/build/artifacts).

## Description

This plugin enables analysis of Groovy within SonarQube.

It leverages [CodeNarc](http://codenarc.sourceforge.net/) to raise issues
against coding rules and [GMetrics](http://gmetrics.sourceforge.net/) for
cyclomatic complexity.

For code coverage, the SonarQube [JaCoCo](http://www.eclemma.org/jacoco/)
plugin should be used. Additionally, this plugin still supports importing
binary JaCoCo reports (deprecated, will be removed in the future) and
[Cobertura](http://cobertura.sourceforge.net/).

Plugin    | 1.4/1.5 | 1.6     | 1.7
----------|---------|---------|-----
CodeNarc  | 0.25.2  | 1.4     | 1.4
GMetrics  | 0.7     | 1.0     | 1.0
SonarQube | 5.6-6.7 | 6.7-7.9 | 7.8-8.3

## Steps to Analyze a Groovy Project
1. Install SonarQube Server
1. Install SonarQube Scanner and be sure you can call `sonar-scanner` from the directory where you have your source code
1. Install the Groovy Plugin.
1. Create a _sonar-project.properties_ file at the root of your project
1. Run `sonar-scanner` command from the project root dir
1. Follow the link provided at the end of the analysis to browse your project's quality in SonarQube UI

## Notes

*CodeNarc*: It is possible to reuse a previously generated report from CodeNarc
by setting the `sonar.groovy.codenarc.reportPaths` property.

*Groovy File Suffixes*: It is possible to define multiple groovy file suffixes
to be recognized by setting the `sonar.groovy.file.suffixes` property. Note
that by default, only files having `.groovy` as extension will be analyzed.

*Unit Tests Execution Reports*: Import unit tests execution reports (JUnit XML
format) by setting the sonar.junit.reportPaths property. Default location is
_target/surefire-reports_.

*JaCoCo and Binaries*: The groovy plugin requires access to source binaries
when analyzing JaCoCo reports. Consequently, property `sonar.groovy.binaries`
has to be configured for the analysis (comma-separated paths to binary
folders). For Maven and gradle projects, the property is automatically set.

## Coverage Results Import

For coverage, it is recommended to use the generic [SonarQube JaCoCo
plugin](https://community.sonarsource.com/t/coverage-test-data-importing-jacoco-coverage-report-in-xml-format/12151)
instead of relying on this plugin to import coverage into SonarQube.
Nevertheless, we support importing coverage from Cobertura (but this code path
isn't used by the author of the plugin).

### Code Coverage with Cobertura

To display code coverage data:

1. Prior to the SonarQube analysis, execute your unit tests and generate the
   Cobertura XML report.
1. Import this report while running the SonarQube analysis by setting the
   `sonar.groovy.cobertura.reportPath` property to the path to the Cobertura
   XML report. The path may be absolute or relative to the project base
   directory.

## Contributions

Contributions via GitHub [issues] and pull requests are very welcome. This
project tries to adhere to the [Google Java Style], but we don't want a global
reformat to keep the Git history readable. To help with this, you can use the
[fmt-maven-plugin] to format your changes:

    mvn fmt:format -DfilesNamePattern=TestUtils\.java

You can use the `fileNamePattern` option to restrict the formatter to the files
you changed.

[issues]: https://github.com/Inform-Software/sonar-groovy/issues/new
[Google Java Style]: https://google.github.io/styleguide/javaguide.html
[fmt-maven-plugin]: https://github.com/coveo/fmt-maven-plugin

### Updating CodeNarc

In the directory `codenarc-converter` there is a little helper tool to convert
CodeNarc rules to SonarQube rules. To do its job it needs a source copy of
CodeNarc - this is currently achieved by including the used CodeNarc version as
a git subbmodule. If you need to update CodeNarc, you need to update that
submodule too:

```
git submodule init
cd codenarc-converter/CodeNarc
git checkout vX.Y.Z
cd ..
git add CodeNarc
```

You should then run the `codenarc-converter` (Running `mvn verify` should be
enough if the project is set up correctly) and merge descriptions from
`codenarc-converter/target/results/rules.xml` into
`sonar-groovy-plugin/src/main/resources/org/sonar/plugins/groovy/rules.xml`.
The converter does a pretty crude job converting CodeNarc's [APT] documentation
into SonarQube rule descriptions.

[APT]: https://maven.apache.org/doxia/references/apt-format.html
