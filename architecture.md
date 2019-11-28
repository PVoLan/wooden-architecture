This file uses markdown formatting. Use a markdown reader (github/bitbucket/gitlab web interfaces can do it typically) to get nice formatted text.

# Key concepts

## No circular dependencies

Circular dependencies is a pain. Beware of them at any price.

Let you have an object A. A calls an object B to do something. B calls an object C. C call an object D. This is ok.

Circular dependency happens when an object D calls an object A to do something. As a react, object A may ask B to do something more, than B calls C, than C calls D again, than D calls A, A calls B, B calls C, C calls A (unexpectedly?)... and so on. This behavior is very difficult to read, understand and debug.

The word "object" here typically refers to an object as it is meant by object-oriented programming, and may be interpreted as an Java-object, for example. So "A calls B" here means something like this

```java
import com.somepackage.B;

class A {

  B b;

  function doSomething(){
    b.doSomethingToo();
  }
}
```

We also can say that A depends on B in that case (do you see the import required to compile A.java?)

But this concept is not limited to OOP-object. If ```methodA()``` calls ```methodB()``` you may say that ```methodA()``` depends on ```methodB()```. Having a circular dependency for methods typically means a recursion. Recursion is a great thing sometimes, but if one recurrence consists of multiple method calls (especially if methods are placed in different block of code far from each other) it is very difficult to detect and debug.

If can also treat "object A" as any module of you application, such a gradle module, running instance, particular server, or something.


## Tiers

So, to beware from circular dependencies whole application is splitted into several tiers (some sources also call it "layers"). Concept is similar to multitier architecture, but particular tier roles are some different from [classic structure](https://en.wikipedia.org/wiki/Multitier_architecture).

![](./tiers.png)

Every tier contains one or more components. For Android application every component is represented with some Java object/class, typically, but not limited to. Typically every component is placed into separate Java-package.

Sometimes it is possible for a component to consist of two or more Java classes/objects. In this case make sure that only one "main" class/object has public visibility. Other classes in a components are threated as "helper" classes and should be package-private (or at least you should think about them as a package private). Helper entity classes/object are allowed to be public (see Entities section below). If you don't understand this paragraph, just ignore it for a while ans use a "one component = one class/object" as a rule

There are general rules about components:
- Different components inside of one tier are independent of each other. They don't even know about each other's existence. So, ```Component 1.1``` has no idea about ```Component 1.2```, and vice versa
- Any component of a tier may depend on any components of next tier. ```Component 1.X``` may depend on any ```Component 2.X```. Any ```Component 1.X``` may call any methods of ```Component 2.X```
- 


## Captain and a deckhand
asdasdas*d*fggdg

## No static objects/references

# General Architecture description

# Android details

   vxcvcx
Unit

# Threading

# Enhancements - events

# Enhancements - multiple logic tiers


//TODO - Billfold refinings
