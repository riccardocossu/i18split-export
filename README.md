![](https://maven-badges.herokuapp.com/maven-central/net.riccardocossu.i18split/i18split-export/badge.svg "Maven central")

![](https://codeship.com/projects/944d1370-e8eb-0132-94e8-1298b11bde3f/status?branch=master)

# i18split-export
This is a collection of plugin for i18split to let developer export and import their files to/into spreadsheet files (xls, ods ...) and maybe other formats in the future.


## Why export at all?

We still think CSV is a very handy format for i18n files: it's text based, so it's easily versionable, mergeable and so on. In a word it is very **developer friendly**. So CSV will probably be the preferred source format, but why don't we add an export/import function? It will be called only when needed, under the accurate supervision by a developer (please see the note at the end for an example to make it work).

But, what if you need to share the file with someone who is not a developer, but, for example, a translation expert or agency; they won't love CSV as you do, that's a fact, they would probably prefer a more user friendly format like XLS or ODS!

## i18split no worries

i18split is flexible enough to transform files to and from different formats.
Let's see an example of ODS/XLS export:

```
<plugin>
				<groupId>net.riccardocossu.i18split</groupId>
				<artifactId>i18split-maven-plugin</artifactId>
				<version>1.0.0</version>
				<dependencies>
					<dependency>
						<groupId>net.riccardocossu.i18split</groupId>
						<artifactId>i18split-export</artifactId>
						<version>1.0.0</version>
					</dependency>
				</dependencies>
				<configuration>
					<inputBasePath>${basedir}/src/main/i18n</inputBasePath>
					<outputBasePath>${project.build.directory}/i18n</outputBasePath>
				</configuration>
				<executions>
					<execution>
						<id>export</id>
						<goals>
							<goal>convert</goal>
						</goals>
						<configuration>
							<inputPlugin>csv.input</inputPlugin>
							<outputPlugin>ods.output</outputPlugin> <!-- use xls.output for XLS -->
							<pluginsConfig>
								<i18split.output.file>messages.ods</i18split.output.file> <!-- change file extension to xls to use xls -->
								<i18split.input.file>messages.csv</i18split.input.file>
							</pluginsConfig>
						</configuration>
					</execution>
				</executions>
			</plugin>

```

And one for import from ODS/XLS:

```

<plugin>
				<groupId>net.riccardocossu.i18split</groupId>
				<artifactId>i18split-maven-plugin</artifactId>
				<version>1.0.0</version>
				<dependencies>
					<dependency>
						<groupId>net.riccardocossu.i18split</groupId>
						<artifactId>i18split-export</artifactId>
						<version>1.0.0</version>
					</dependency>
				</dependencies>
				<configuration>
					<inputBasePath>${basedir}/src/main/i18n/import</inputBasePath>
					<outputBasePath>${project.build.directory}/i18n</outputBasePath>
				</configuration>
				<executions>
					<execution>
						<id>import</id>
						<goals>
							<goal>convert</goal>
						</goals>
						<configuration>
							<outputPlugin>csv.output</outputPlugin>
							<inputPlugin>ods.input</inputPlugin> <!-- use xls.input for XLS -->
							<pluginsConfig>
								<i18split.input.file>messages.ods</i18split.input.file> <!-- change file extension to xls to use xls -->
								<i18split.output.file>messages.csv</i18split.output.file>
							</pluginsConfig>
						</configuration>
					</execution>
				</executions>
			</plugin>


```

## A note about maven

For some strange reason, although the execution tag needed an unique id since ever, prior to maven 3.3 there was no way of directly calling a specific execution defined in the pom, without adding a phase in the pom declaration or introducing a specific profile.
In maven 3.3 you can call the export/import execution anytime, with a command like this:

```
mvn i18split:convert@export
```
where **export** is the id of the execution.
