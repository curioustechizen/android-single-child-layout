##Note:

[`ViewAnimator`](http://developer.android.com/reference/android/widget/ViewAnimator.html), which is already included in the Android SDK covers almost all the use cases of this library. You might want to use `ViewAnimator` instead.

android-single-child-layout
===========================

A tiny(~1.5KB), simple Android layout that has several children, but only shows a single child at a time. See the documentation for the class `com.github.curioustechizen.scl.SingleChildLayout` for details. 

###Usage

Use one of the following methods:

  - Add the JAR file from [downloads](downloads) folder as a dependency for your app project.
  - Add the android-single-child-layout as an Android Library project dependency for your app project.
  - Coming soon: Maven artifact (Any help on this is appreciated!)

###Why not `FrameLayout`?
 
This class actually extends from `FrameLayout`, and is not meant to replace it. The latter is useful when you want to add views one one top of the other. However, `FrameLayout` is also often used when you want to show only one of its children at a time.

A typical use case is when you want to replace some view with a ProgressBar temporarily. This extends to cases where there are more than two states and you want to show one of the children of the `FrameLayout` depending on the state. This is where `SingleChildLayout` is useful.


###Samples

There are two sample projects:

  - [Basic](android-single-child-layout-sample): This sample demonstrates a bare minimum use case for SCL.
  - [ListView] (android-single-child-layout-list-sample): This sample is more "real-worldly". In a typical screen that shows a `ListView`, you might also have a special view for showing the empty state. The `AdapterView` class already has a `setEmptyView` method to account for this - but what if you have more states? For example, what if you have a separate "loading" state and, say, an "initial" state? This example shows how you might use SCL in such a situation, to make it easy to show one state at a time.
  

###ToDo:

  - Currently, we only support showing a child by index. Consider expanding the SCL API to allow showing a child using its View ID (`showChildWithId(int viewId)` ??). 
  
