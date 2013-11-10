android-single-child-layout
===========================

A simple Android layout that has several children, but only shows a single child at a time. See the documentation for the class `com.github.curioustechizen.scl.SingleChildLayout` for details. See the project android-single-child-layout-sample for an example.


###Why not `FrameLayout`?
 
This class actually extends from `FrameLayout`, and is not meant to replace it. The latter is useful when you want to add views one one top of the other. However, `FrameLayout` is also often used when you want to show only one of its children at a time.

A typical use case is when you want to replace some view with a ProgressBar temporarily. This extends to cases where there are more than two states and you want to show one of the children of the `FrameLayout` depending on the state. This is where `SingleChildLayout` is useful.
