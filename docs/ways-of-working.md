# Ways of Working

## Git branching model
We have adopted the GitFlow Branching Model in order to support both Gaffer v1 and v2: ![GitFlow Branching Model](https://nvie.com/img/git-model@2x.png)

## Issues 
Where possible a pull request should correlate to a single GitHub issue. An issue should relate to a single functional or non-functional change - changes to alter/improve other pieces of functionality should be addressed in a separate issue in order to keep reviews atomic.
The reasoning behind code changes should be documented in the GitHub issue.
All resolved issues should be included in the next GitHub milestone, this enables releases to be linked to the included issues.
If a code change requires users of Gaffer to make changes in order for them to adopt it then the issue should be labelled 'migration-required' and a comment should be added similar to:

```
### Migration Steps

[Description of what needs to be done to adopt the code change with examples]
```

### Workflow
* Assign yourself to the issue
* Create a new branch off **develop** using pattern: `gh-[issue number]-[issue-title]`
* Commit your changes prefixing your descriptive commit title, like so: `gh-[issue-number]: [commit title]`
* Check and push your changes
* Create a pull request (PR) to merge your branch into **develop**
* If you named the branch and PR correctly, the PR should have "Resolve #[issue-number]" automatically added to the description after it is made. If it doesn't, then please add the issue it will resolve as a "Linked issue"
* If there is a significant change, please follow the same process to document the change in [gaffer-doc](https://github.com/gchq/gaffer-doc)
* The pull request will be reviewed and following any changes and approval your branch will be squashed and merged into **develop**
* Delete the branch
* The issue will be closed automatically

## Pull Requests
Pull requests will undergo a review by a Gaffer committer to check the code changes are compliant with our coding style. This is a community so please be respectful of other members - offer encouragement, support and suggestions. 

As described in our git branching model - please raise pull requests to merge you changes in our **develop** branch.

When pull requests are accepted, the reviewer should squash and merge them. This is because it keeps the **develop** branch clean and populated with only merge commits, rather than intermediate ones. As well as this, it makes everyone's job reviewing pull requests easier as any insecure and unreviewed intermediate commits are not included into the **develop** branch.

Please agree to the [GCHQ OSS Contributor License Agreement](https://github.com/GovernmentCommunicationsHeadquarters/Gaffer/wiki/GCHQ-OSS-Contributor-License-Agreement-V1.0) before submitting a pull request. Signing the CLA is enforced by the cla-assistant.

## Documentation
As mentioned before, any significant changes in a PR should be accompanied with an addition to Gaffer's documentation: [gaffer-doc](https://github.com/gchq/gaffer-doc).
Smaller changes should be self documented in the tests. With this approach, any large feature or change has user friendly documentation, whereas technical or implementation details are documented for developers by the tests.

## Coding style
Please ensure your coding style is consistent with rest of the Gaffer project and the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html). Your changes should pass the checkstyle and spotless plugin checks when running `mvn install`, these check for code formatting and licenses and are part of the continuous integration pipeline.

### Javadoc
Ensure your java code has sufficient javadocs explaining what the section of code does and the intended use of it. Javadocs should be used in addition to clean readable code.

In particular:
* All public classes (not required for test classes unless an explanation of the testing is required)
* public methods (not required if the functionality is obvious from the method name)
* public constants (not required if the constant is obvious from the name)

### Tests
* All new code should be unit tested. Where this is not possible the code should be invoked and the functionality should be tested in an integration test. In a small number of cases this will not be possible - instead steps to verify the code should be thoroughly documented.
* Tests should cover edge cases and exception cases as well as normal expected behavior.
* Keep each test decoupled and don't rely on tests running in a given order - don't save state between tests.
* For a given code change, aim to improve the code coverage.
* Unit test classes should test a single class and be named [testClass]Test.
* Integration test classes should be named [functionalityUnderTest]IT.
* Tests should be readable and self documenting.
* Each test should focus on testing one small piece of functionality invoked from a single method call.
* Tests should use JUnit 5 and assertJ.
* We suggest the following pattern:

  ```java
  @Test
  public void should[DoSomething|ReturnSomething] {
      // Given
      [Setup your test here]

      // When
      [Invoke the test method]

      // Then
      [assertThat the method did what was expected]
  }
  ```

## Gaffer 2
During the Gaffer 2 development process there is a **v2-alpha** branch, which acts as the **develop** branch for changes that will only be added to Gaffer 2.
