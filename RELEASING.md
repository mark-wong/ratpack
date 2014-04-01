Release instructions.

(a) - denotes something that is a candidate for automation

## Release announcement

The announcement for the release is the description of the GitHub milestone.
Use the GitHub UI to write it.
You can use Markdown.

Write the content, but don't close the milestone yet.

## Pre

1. Ensure there is a next (version after what is being released) milestone on GitHub with a date set (1st of next month)
1. Check that there are no outstanding reviews for commits for the current versions, or at least that any issues don't block the release
1. Check that there are no outstanding issues/pull requests for the development version (either implement or move them to next milestone)
1. (a) Ensure that there are no -SNAPSHOT dependencies (or if there are, that there is good reason for them)
1. Ensure the the build is passing (i.e. run `./gradlew clean build`)

## Go time…

1. (a) Update `shared-resources/ratpack/ratpack-version.txt` (i.e. drop the -SNAPSHOT)
1. Ensure the the build is still passing (i.e. run `./gradlew clean build`) - really isn't needed, but doesn't hurt
1. Commit with message “Version «number»”
2. Tag commit with name “v«number»” (don't push yet)
1. Build and upload the binaries: `./gradlew artifactoryPublish` - See below for credential requirements
1. Promote the binaries from oss.jfrog.org to Bintray and Maven Central
    1. Go to https://oss.jfrog.org/webapp/builds/ratpack/?6
    1. Find the build you just uploaded (you should be able to tell by the version number)
    1. After clicking the build, the next page has an 'Upload to Bintray' icon, click it
        1. Details: repository = ratpack/maven, package name = ratpack, version = «release version»
        1. I often have to repeat this process a few times because it times out, until it finishes with success
    1. Confirm the publish in Bintray - The link to the bintray page is given on the success page of the previous step. Just in case it's:  https://bintray.com/ratpack/maven/ratpack/«version»/view/files/io/ratpack
    1. Publish to Maven central - click the 'Maven Central' tab on the Bintray package page
        1. Enter your user/pass
        1. Click “Close repository when done”
        1. Click “Sync”

## Post

1. (a) Update `shared-resources/ratpack/ratpack-version.txt` (i.e. increment the patch number and add -SNAPSHOT)
1. Update the `manualVersions` list in `ratpack-site.gradle` so the new manual is included in the site
1. Update the `RatpackSiteUnderTest` class to include the next development version, and mark the just released version as released
1. Update the `ratpack-site/src/ratpack/templates/index.html` file to use the new version number for the Groovy example
1. Commit with message 'Begin version «version»', and push (make sure you push the tag)
1. Close the Milestone on GitHub
1. Get a tweet out about the release

## Credentials needed

1. Ability to edit milestones for the `ratpack/ratpack` GitHub project (it might be that only admins can do this, not sure)
1. Credentials for oss.jfrog.org
    1. This is your Bintray account - use the Bintray UI to ask for write permissions to the io.ratpack group in oss.jfrog.org
1. GPG credentials/config
    1. We use the Gradle Signing Plugin to sign the artifacts (we don't let Bintray do this) - See [the Gradle docs](http://www.gradle.org/docs/current/userguide/signing_plugin.html#N15692) for how to set this up
1. oss.sonatype.org credentials
    1. The sync from Bintray to Central requires an account with oss.sonatype.org
    1. See [the docs](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-2.Signup) for creating an account here
    1. Add a comment to [this JIRA ticket](https://issues.sonatype.org/browse/OSSRH-8283) with your new account, asking for permission to publish to `io.ratpack`.