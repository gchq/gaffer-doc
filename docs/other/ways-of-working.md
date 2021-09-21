# Ways of Working

## Contents
1. [Git branching model](#git-branching-model)
2. [Issues](#issues)
3. [Pull requests](#pull-requests)
4. [Coding style](#coding-style)

## Git branching model
We have adopted the following branching strategy: [Git Branching Model](https://nvie.com/files/Git-branching-model.pdf)

## Issues 
Where possible a pull request should correlate to a single GitHub issue. An issue should relate to a single functional or non-functional change - changes to alter/improve other pieces of functionality should be addressed in a separate issue in order to keep reviews atomic.
The reasoning behind code changes should be documented in the GitHub issue. 
All resolved issues should be included in the next GitHub milestone, this enables releases to be linked to the included issues.
If a code change requires users of Gaffer to make changes in order for them to adopt it then the issue should be labelled 'migration-required' and a comment should be added similar to:

```
### Migration Steps

[Description of what needs to be done to adopt the code change with examples]
```

#### Workflow
* Assign yourself to the issue
* Create a new branch off **develop** using pattern: `gh-[issue number]-[issue-title]`
* Commit your changes prefixing your descriptive commit title, like so: `gh-[issue-number] - [commit title]`
* Check and push your changes
* Create a pull request (PR) to merge your branch into **develop**
* Assign the in-review label to your issue
* If you named the branch and PR correctly, the PR should have "Resolve #[issue-number]" automatically added to the description after it is made. If it doesn't, then please add the issue it will resolve as a "Linked issue"
* The pull request will be reviewed and following any changes and approval your branch will be squashed and merged into **develop**
* Delete the branch
* The issue will be closed automatically

## Pull Requests
Pull requests will undergo an in depth review by a Gaffer committer to check the code changes are compliant with our coding style. This is a community so please be respectful of other members - offer encouragement, support and suggestions. 

As described in our git branching model - please raise pull requests to merge you changes in our **develop** branch.

When pull requests are accepted, the reviewer should squash and merge them. This is because it keeps the **develop** branch clean and populated with only working commits, rather than intermediate ones. As well as this, it makes everyone's job reviewing pull requests easier as any insecure and unreviewed intermediate commits are not included into the **develop** branch.

Please agree to the [GCHQ OSS Contributor License Agreement](https://github.com/GovernmentCommunicationsHeadquarters/Gaffer/wiki/GCHQ-OSS-Contributor-License-Agreement-V1.0) before submitting a pull request. Signing the CLA is enforced by the cla-assistant.

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
