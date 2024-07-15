# User Authentication

!!! info "Work in Progress"

    This page is under construction.

The security layer for Gaffer is currently only enforced by the REST API. We
recommend restricting users such that they do not have access to the cluster and
by only allowing users to connect to Gaffer via the REST API.

In the REST API the `User` object is constructed via a [`UserFactory`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/rest/factory/UserFactory.html).
Under the Spring REST API this has an abstract implementation class
`AbstractUserFactory` to allow passing HTTP headers to be used for
authentication.

Currently, we only provide one implementation of this, the `UnknownUserFactory`
which will always just return `new User()`. The `UnknownUserFactory` is the default
factory, to specify the user factory class define the
`gaffer.user.factory.class` [REST property](../gaffer-config/config.md#application-properties).

## Writing a User Factory

To authenticate your users, you will need to extend the `AbstractUserFactory` class
to add your chosen authentication mechanism. The hooks will already be in the REST API
to pass the current HTTP headers for each request. Your factory will need to parse these
to construct a new `User` object via the `createUser()` method that reflects the user
making the request. This could involve making a call to an LDAP server or similar
authentication service.

For example, you could use the authorisation header in the request:

```java
public class LdapUserFactory extends AbstractUserFactory {

    public User createUser() {
        final String authHeaderValue = this.httpHeaders.get(HttpHeaders.AUTHORIZATION); // add logic to fetch userId
        final String userId = null; // extract from authHeaderValue
        final List<String> opAuths = null; // fetch op auths for userId
        final List<String> dataAuths = null; // fetch op auths for userId

        // Create and return the Gaffer user
        return new User.Builder()
                .userId(userId)
                .opAuths(opAuths)
                .dataAuths(dataAuths)
                .build();
    }
}
```
