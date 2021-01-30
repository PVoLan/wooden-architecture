# Wooden architecture

An architecture guide for Android applications. Sample code included.

### Table of contents

[1. Key concepts](./Chapter1/index.md)  
[2. General architecture description](./Chapter2/index.md)  
[3. Android implementation details (Work in progress)](README.md)  
[4. Possible enhancements and improvements (Work in progress)](README.md)  

[Sample app](./WASampleApp)  

### Introduction

Hello everybody!

I develop Android applications professionally since 2011. Over the years I've elaborated a set of architectural techniques and approaches which I use in almost every app I develop and which make my code stable and agile. Now I'd like to share these techniques with you.

I've called it "Wooden architecture" - because I find it as straightforward and reliable as rude wooden houses construction. Most probably you're not going to build a skyscraper with woods, but it is pretty enough for any application of small and medium size, what are 90% of Android apps.

### The purpose

First of all, the main motivation to write this article was actual lack of architecture guides for mobile applications. Despite the fact Android OS became popular development platform about 10 years ago, there is no any single, agile enough, widely-used and well-defined architectural approach for Android apps. In order to fill this lack, the current guide fully describes the suggested architecture, starting from high-level concepts and finishing with code examples. I hope to make it easy to read and understand for developers with medium experience.

### Comparison between Wooden architecture and other architecture approaches

Here is a list of known architecture approaches and comparison of these approaches with Wooden architecture:

- [MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller), [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter), [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel), MVI, and others. These are not an architectures, these are architecture patterns. They describe only one aspect of application architecture: relation between View and Model. At the same time, even the definition of "what is View" and "what is Model" varies from developer to developer and from project to project. For example, some understand View as `android.view.View` derivatives, and Controller as `Activity`. Others understand View as `Activity` and Controller as a separated object, which provides data to `Activity`. The lack of good definitions leads to numerous misunderstandings. Also, the difference between MV* approaches is not always clear.  

  For ones who still feels familiar and confident with MV* patterns, I could say that Wooden architecture uses some concepts of MVP and, maybe, MVVM as a part.

- [Google architecture guide](https://developer.android.com/jetpack/guide). This is an "official" guide published by Google at about 2019, as far as I remember. Despite it was published too late (Wooden architecture was born years before :), it is still a good attempt to bring an order to the architectural chaos in Android. Wooden architecture structure at a first glance may look very similar to this [picture](https://developer.android.com/jetpack/guide#overview), but some significant differences are also met.

  The Google architecture part related to Activity/ViewModel and corresponding part of Wooden architecture are almost identical by essense. But I find Google's Repository and other Model fundamentals too rigid: why do they think that the only thing what Android application does is data fetching and storing? Where is the business logic? Why do the treat server-side just as "Remote data source"? What about user actions and data processing?

  If you will replace Google's Repository with Wooden's UseCase, you will find that these architecture structures are still quite similar. But Wooden architecture is more agile, gives you more strict modules definition and leaves you more space to grow.

  Another disadvantage of Google's guide is that it is too tight to third-party libraries, such as Retrofit, Dagger and others. Although there is nothing wrong with using third-party libraries when actually needed, your architecture should be independent from implementation details.

- Clean architecture. When someone talks about Clean architecture, this [picture](https://medium.com/android-dev-hacks/detailed-guide-on-android-clean-architecture-9eab262a9011) usually pops up in minds. Some developers think that Clean architecture was developed by Google and is another "official" Android guide, but it isn't true: it became popular few years ago after someone has mentioned Clean architecture at one of the Google conferences, but it was not developed by Google.

 First what you should understand about Clean architecture: it is a [book](https://www.oreilly.com/library/view/clean-architecture-a/9780134494272/) by Robert C. Martin about 400 pages thick. It is a great book you definitely should read, and what you should know as an Android developer is that this [picture](https://medium.com/android-dev-hacks/detailed-guide-on-android-clean-architecture-9eab262a9011) is just a small part of this book. Instead, a huge part of the book is dedicated to declaring of key principles like SOLID, dependencies flow, business rules, software boundaries, and many others.

 Many concepts declared by Robert C. Martin are used in Wooden architecture: strict module boundaries and dependencies flow are defined. At the same time, I find that strict following of Clean architecture is redundant for typical small and medium-sized Android project. Using strict dependency inversion and IoC rules is useless in most cases: in real life you rarely change database framework or API protocol fundamentals, also it is quite rare that you start using Android business classes on another (non-Android) platform. Although it is a good point to leave option for unexpected changes, you should not overengineer to cover cases will never happen. Also, DI is counter-intuitive and hard to understand by junior developers.

 So, Wooden architecture follows some Clean rules and omits others. But, what is important and shown in the guide: due to strict boundaries, Wooden architecture can be easily refactored to pure Clean architecture, if needed.

### Guide description

Wooden core concepts can be used not only for Android applications, but theoretically for any platform. Wooden architecture was originally based on a server-side backend architecture I was participating in, later was successfully used in crossplatform iOS/Android applications, Windows Desktop application and even in a small PHP server. But surely this guide is speaking mostly about Android.

The guide is accompanied with a [sample app](./WASampleApp) code. While reading the guide you can refer to sample code to see how the approaches described work in practice. The app is a simple weather forecast application: you enter a city name and receive a weather forecast. Forecasts downloaded are cached in a local database for some time. Application also shows a marker if the weather is good enough to go outside for some running: what is "good enough" is defined by user via "Settings" screen.

Application uses [OpenWeatherMap API](https://openweathermap.org/api) to get a forecast. If one day you'll find this API down, or you will experience network issues, or you'd like to perform some tests, a "Fake API" is also provided - see [WASampleApp/d_container/src/main/java/ru/pvolan/container/Container.java](./WASampleApp/d_container/src/main/java/ru/pvolan/container/Container.java) to switch on it.

Disclaimer. _A good architecture comes from understanding it more as a journey than as a destination. Robert C. Martin._ Although this guide is quite practical and detailed, I'm not going to bring you to some "once and for all" solution. I'm just showing you the path I've personally walked during my journey. It is your choice to follow me or find your own way. Some implementation details may require adjustment to your particular project.  

[Okay, let's go inside!](./Chapter1/index.md)  
