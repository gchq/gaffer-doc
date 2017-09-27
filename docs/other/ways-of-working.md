# Ways of Working

## Contents
1. [Git branching model](#git-branching-model)
2. [Issues](#issues)
3. [Pull requests](#pull-requests)
4. [Coding style](#coding-style)
5. [Releases](#releases)

## Git branching model
We have adopted the following branching strategy: [Git Branching Model](http://nvie.com/files/Git-branching-model.pdf)

## Issues 
Where possible a pull request should correlate to a single GitHub issue. An issue should relate to a single functional or non-functional change - changes to alter/improve other pieces of functionality should be addressed in a separate issue in order to keep reviews atomic.
The reasoning behind code changes should be documented in the GitHub issue. 
All resolved issues should be included in the next GitHub milestone, this enables releases to be linked to the included issues.
If a code change requires users of Gaffer to make changes in order for them to adopt it then the issue should be labelled 'migration-required' and a comment should be added similar to:

```md
### Migration Steps

[Description of what needs to be done to adopt the code change with examples]
```

#### Workflow
* Assign yourself to the issue
* Create a new branch off develop using pattern: gh-[issue number]-[issue-title]
* Commit your changes prefixing your commit title with: gh-[issue-number] - [commit title]
* Check your changes
* Create a pull request to merge your branch into develop (and assign label in-review to your issue)
* The pull request will be reviewed and following any changes and approval your branch will be merged into develop
* Delete the branch
* Close the issue - add a comment saying it has been merged into develop

## Pull Requests
Pull requests will undergo an in depth review by a Gaffer committer to check the code changes are compliant with our coding style. This is a community so please be respectful of other members - offer encouragement, support and suggestions. 

As described in our git branching model - please raise pull requests to merge you changes in our **develop** branch.

Please agree to the [GCHQ OSS Contributor License Agreement](https://github.com/GovernmentCommunicationsHeadquarters/Gaffer/wiki/GCHQ-OSS-Contributor-License-Agreement-V1.0) before submitting a pull request.

## Coding style
Please ensure your coding style is consistent with rest of the Gaffer project and follow coding standards and best practices.

In particular please ensure you have adhered to the following:
* Strive for small easy to read and understand classes and methods.
* Separate out related classes into packages and avoid highly coupled classes and modules.
* Checkstyle and findbugs are run as part of 'mvn package' so you should ensure your code is compliant with these. The project will not build if there are issues which cause these plugins to fail.
* Classes and methods should comply with the single responsibility principal.
* Avoid magic numbers and strings literals.
* Avoid duplicating code, if necessary refactor the section of code and split it out into a reusable class.
* Look after you streams - if you open one make sure you close it too. This should apply to all volatile resource usage.
* Don't swallow exceptions - ensure they are logged or rethrown.
* Give credit for other peoples work.
* Update the NOTICES file for changes to the dependencies.
* Consider the scope of dependencies - restrict them when possible using the appropriate maven scope.
* Don't expose private logic in classes through public methods.
* Try to avoid static classes/methods.
* Field access should be controlled via getters and setters.
* Make use of the core Java API - don't reinvent the wheel.
* Make use of generic typing.
* Make use of appropriate object oriented design patterns.
* Use Loggers instead of System.out.print and throwable.printStackTrace.
* Ensure that the toString(), equals() and hashCode() methods are implemented where appropriate.

#### Javadoc
Ensure your java code has sufficient javadocs explaining what the section of code does and the intended use of it. Javadocs should be used in addition to clean readable code, it is not an excuse to write lazy code.

In particular:
* All public classes (not required for test classes unless an explanation of the testing is required)
* public methods (not required if the functionality is obvious from the method name)
* public constants (not required if the constant is obvious from the name)

#### Tests
* All new code should be unit tested. Where this is not possible the code should be invoked and the functionality should be tested in an integration test. In a small number of cases this will not be possible - instead steps to verify the code should be thoroughly documented.
* Tests should cover edge cases and exception cases as well as normal expected behavior.
* Keep each test decoupled and don't rely on tests running in a given order - don't save state between tests.
* Overall for a given code change aim to improve the code coverage.
* Unit test classes should test a single class and be named [testClass]Test.
* Integration test classes should be named [functionalityUnderTest]IT.
* Tests should be readable and self documenting where possible. 
* Each test should focus on testing one small piece of functionality invoked from a single method call. 
* Unit tests should use JUnit 4.x.
* We suggest the following pattern:

  ```java
  @Test
  public void should[DoSomething|ReturnSomething] {
      // Given
      [Setup your test here]

      // When
      [Invoke the test method]
  
      // Then
      [assert the method did what was expected]
  }
  ```

## Releases
* All issues included in the release should be marked with the relevant milestone
* Check the javadoc is all still valid - mvn clean install -Pquick && mvn javadoc:javadoc
* Run the following (assuming develop is ahead of master), replacing [version] with the release version:
  
  ```bash
  # Create release branch
  git reset --hard
  git checkout develop
  git pull
  git checkout -b release-[version]

  # Run following the release:prepare goal to update the pom version and tag the code - when prompted enter [version]-RC1 for the version.
  mvn release:prepare release:clean 
  ```

* Test the release
* Make any changes required by creating issues, branching off the release branch and creating pull requests back into the release branch.
* Release the next RC version using the following:

  ```bash
  git reset --hard
  git checkout release-[version]
  git pull
  mvn release:prepare release:clean
  ```

* When no further bugs run the release:prepare goal a final time and set the version to [version] without the RC bit.
* Create a pull request to merge the release branch into master
* Merge master into develop:

  ```bash
  git checkout master
  git pull
  git checkout develop
  git pull
  git checkout -b update-develop
  git merge master
  git push -u origin update-develop
  # Create a pull request to merge update-develop into develop
  ```

* Update the Javadoc

  ```bash
  git checkout gaffer2-[version]
  mvn clean install -Pquick && mvn javadoc:javadoc scm-publish:publish-scm
  ```

* Create a release in GitHub (Gaffer [version]) containing links to the resolved issues, similar to:

  ```
  [0.3.2 issues resolved](https://github.com/gchq/Gaffer/issues?q=milestone%3Av0.3.2)

  [0.3.2 issues with migration steps](https://github.com/gchq/Gaffer/issues?q=milestone%3Av0.3.2+label%3Amigration-required)
  ```

* We are currently investigating the use of the maven release plugin to push artefacts up to a repository