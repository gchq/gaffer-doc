${HEADER}

${CODE_LINK}

when running an Operation, for example GetElements, within Gaffer you can give it a View.

In the view you can set filters, properties to include, and properties to exclude, on different groups.

If you want to set the same filters on multiple groups previously it would be a case of copying and pasting. Now you are able to use the global definition section.
To avoid complicating things for the rest of the internals of Gaffer we must 'expand' out these global filters before the View is passed to the internals of Gaffer.

In this example we will explain all uses, including overriding and concatenating, within filters and properties.

If we want to query for RoadUse edges containing vertex `”10”` and with a count of less than `”count”` > 2 one time we can do this:

${GET_GROUP_SPECIFIC_FILTER_SNIPPET}

This will return these results:

```
${GET_ELEMENTS_WITH_GROUP_SPECIFIC_FILTER}
```

This can also be written using a global filter, that will apply the filter to all groups.  An example can be seen below:

${GET_GLOBAL_FILTER_SNIPPET}

Group specific filters can also be applied on top of global filters, and these are concatenated using an AND operator.  Lets look at how this will work:

${GET_GLOBAL_AND_GROUP_SPECIFIC_FILTER_SNIPPET}

As you can see above we have applied a global filter (`"count"` > 0), and then applied a group specific filter to the view (`"count"` > 2).  Because these are concatenated with
the AND operator, it reads as `"count"` > 0 && `"count"` > 2.  Take a look at the results below, we can see that it has returned one record:

```
${GET_ELEMENTS_WITH_GLOBAL_AND_GROUP_SPECIFIC_FILTER}
```

This is because the global filter will have returned multiple records, but the group specific filter, concatenated with the AND, means only one record with a `"count"` > 2
is returned.

Properties can also be set globally, using a similar method to the above for filtering.

If we want to include the `"count"` property on a group specific level, this can be done like this:

${GET_GROUP_SPECIFIC_PROPERTY_SNIPPET}

Producing these elements:

```
${GET_ELEMENTS_WITH_GROUP_SPECIFIC_PROPERTY}
```

Similarly, we can do it on a global edge level, applying this property to all edges returned in the GetElements Operation:

${GET_GLOBAL_PROPERTY_SNIPPET}

Producing:

```
${GET_ELEMENTS_WITH_GLOBAL_PROPERTY}
```

In contrast to the filters however, setting group specific properties will completely override the globally set properties.
If there is a global property of `"count"` but then a group specific property of `"doesNotExist"` we will get no properties returned:

${GET_GLOBAL_AND_GROUP_SPECIFIC_PROPERTY_SNIPPET}

```
${GET_ELEMENTS_WITH_GLOBAL_AND_GROUP_SPECIFIC_PROPERTY}
```

Global exclude properties work in the same way as global properties.

If we want to exclude the property `"count"` on a group specific level we can do this:

${GET_GROUP_SPECIFIC_EXCLUDE_PROPERTY_SNIPPET}

This will return all elements that match the other criteria of the GetElements Operation, and exclude the property `"count"`:

```
${GET_ELEMENTS_WITH_GROUP_SPECIFIC_EXCLUDE_PROPERTY}
```

We can also do this on a global level, applying the exclude property `"count"` to all edges:

${GET_GLOBAL_EXCLUDE_PROPERTY_SNIPPET}

This will return the same results as the group specific exclude property:

```
${GET_ELEMENTS_WITH_GLOBAL_EXCLUDE_PROPERTY}
```

Using both global and group specific exclude properties will cause the global excludes to be overwritten.  If we exclude the `"count"` property
on a global level we can then override it on a group specific level, for example excluding the property `"doesNotExist"`.  The example can be seen below:

${GET_GLOBAL_AND_GROUP_SPECIFIC_EXCLUDE_PROPERTY_SNIPPET}

As mentioned above, the global level exclude property has been overridden so we will get the count property in our returned elements:

```
${GET_ELEMENTS_WITH_GLOBAL_AND_GROUP_SPECIFIC_EXCLUDE_PROPERTY}
```



