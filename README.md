# Github-Issue-Tracker
An android application that allows you to view and interact with your  
repository issues using GitHub's Graphql API and Apollo Client.

I use Apollo, which creates clientside stub classes to give us
type-safe api operations. Apollo generates these types
(via gradle plugin) during our builds, based on .graphql files
and a schema.json file.

## Resources
To ge started on graphql and Apollo client, I highly recommend going through
the following documentations and 'Get-started' guides
* https://docs.github.com/en/graphql
* https://www.apollographql.com/docs/android
* https://graphql.org/learn/

## Dependencies
To run this project ensure you have the following dependencies
```
    implementation 'com.apollographql.apollo:apollo-runtime:2.5.5'
    implementation 'com.squareup.okhttp:okhttp:2.5.0'
    implementation 'com.google.android.material:material:1.4.0-alpha01'
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
```
## Version Control Worklflow
I use the "Git-flow" approach: master is the release branch  
it should always be releasable, and only merged into when we have tested  
and verified that everything works and is good to go.
Daily development is done in the development branch. Features,  
bugfixes and other tasks are done as branches off of develop,then merged
back into developed directly or via pull requests.


