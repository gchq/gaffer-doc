# User

When interacting with Gaffer you need to use a [User](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/user/User.html).
The User class is part of Gaffer's [access module](https://github.com/gchq/Gaffer/tree/master/core/access).

## User Object

The `User` object contains:
- `userId` - Unique identifier for the user
- `dataAuths` - User authorisations for accessing data
- `opAuths` - User authorisations for running different Gaffer operations

The `execute` methods of [`Graph`](graph.md) require you to pass in an instance of `User`.
The examples in this documentation usually use the default user created with `new User()`.

For users of Gaffer, interacting in Java is insecure as you are able to set any authorisations you want to. However, as
you are writing Java it is assumed that you have access to the `store.properties` file providing the connection details
(including password) for the cluster where your data is stored (e.g. Accumulo). Therefore, you could bypass Gaffer and
read all the data directly from disk if you wanted to.

The security layer for Gaffer is currently only enforced by the REST API. We recommend restricting users such that they
do not have access to the cluster and by only allowing users to connect to Gaffer via the REST API. In the REST API the
`User` object is constructed via a [`UserFactory`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/rest/factory/UserFactory.html).
Currently, we only provide one implementation of this, the [`UnknownUserFactory`](https://gchq.github.io/Gaffer/uk/gov/gchq/gaffer/rest/factory/UnknownUserFactory.html).
This `UnknownUserFactory` will always just return `new User()`.

To authenticate your users, you will need to extend the REST API to add your chosen authentication mechanism. You will
need to implement your own `UserFactory` class which creates a new `User` instance based on the user making the REST
request. This could involve making a call to an LDAP server or similar auth service.

For example, you could use the authorisation header in the request:
```java
public class LdapUserFactory implements UserFactory {

    @Context
    private HttpHeaders httpHeaders;

    public User createUser() {
        final String authHeaderValue = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION); // add logic to fetch userId
        final String userId = null; // extract from authHeaderValue
        final List<String> opAuths = null; // fetch op auths for userId
        final List<String> dataAuths = null; // fetch op auths for userId
        return new User.Builder()
                .userId(userId)
                .opAuths(opAuths)
                .dataAuths(dataAuths)
                .build();
    }
}
```
