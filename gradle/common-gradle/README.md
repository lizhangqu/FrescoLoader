
### Usage

**download**

download this git repository and copy the \*.gradle to root project dir gralde/\*.gradle

**common_version.gradle**

add this in root project build.gradle

```
buildscript {
    //for buildscript
    apply from: 'gradle/common_version.gradle'

    repositories {
        jcenter()
    }

    dependencies {
        classpath "com.android.tools.build:gradle:${global_androidGradlePluginVersion}"
    }
}

// for project
apply from: 'gradle/common_version.gradle'
```

add this to module project build.gradle in order to reference the value  

```
android {
    compileSdkVersion global_compileSdkVersion
    buildToolsVersion global_buildToolsVersion
    defaultConfig {
        minSdkVersion global_minSdkVersion
        targetSdkVersion global_targetSdkVersion
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile "com.android.support:appcompat-v7:${global_androidSupportVersion}"
    compile "com.android.support.constraint:constraint-layout:${global_androidConstraintLayoutVersion}"
    testCompile "junit:junit:${global_junitVersion}"
}
```

**artifact_release.gradle**

add this in root project build.gradle

```
buildscript {
    //for buildscript
    apply from: 'gradle/common_version.gradle'

    repositories {
        jcenter()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:${global_androidGradlePluginVersion}"
        classpath "com.github.dcendents:android-maven-gradle-plugin:1.5"
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.3'
    }
}

```

add this to module project build.gradle in order to upload it to maven

```
apply from: project.rootProject.file('gradle/artifact_release.gradle')
```


add this to module project gradle.properties so that artifact_release.gradle can find the value

```
POM_GROUP=
POM_ARTIFACT_ID=
POM_VERSION=
POM_DESCRIPTION=
POM_LICENSE=
POM_LICENSE_URL=
POM_WEBSITE_URL=
POM_VCS_URL=
POM_ISSUE_URL=
POM_DEVELOPER_ID=
POM_DEVELOPER_NAME=
POM_DEVELOPER_EMAIL=
```

or

add this to module project build.gradle so that artifact_release.gradle can find the value

```
project.ext{
	POM_GROUP=
	POM_ARTIFACT_ID=
	POM_VERSION=
	POM_DESCRIPTION=
	POM_LICENSE=
	POM_LICENSE_URL=
	POM_WEBSITE_URL=
	POM_VCS_URL=
	POM_ISSUE_URL=
	POM_DEVELOPER_ID=
	POM_DEVELOPER_NAME=
	POM_DEVELOPER_EMAIL=
}
```

**common_function.gradle**

add this in root project build.gradle

```
buildscript {
    //for buildscript
    apply from: 'gradle/common_version.gradle'
    apply from: 'gradle/common_function.gradle'

    repositories {
        jcenter()
    }

    dependencies {
        classpath "com.android.tools.build:gradle:${global_androidGradlePluginVersion}"
    }
}

// for project
apply from: 'gradle/common_version.gradle'
apply from: 'gradle/common_function.gradle'
```

function

```
project.logger.info "isJenkins:${isJenkins()}"
project.logger.info "getGitCommitCount:${getGitCommitCount('master')}"
project.logger.info "getGitTag:${getGitTag('master')}"
project.logger.info "getDate:${getDate()}"
```
