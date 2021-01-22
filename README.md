# Wooden architecture

An architecture guide for Android applications. Sample code included.

# Table of contents

[1. Key concepts](./Chapter1/index.md)  
[2. General architecture description](./Chapter2/index.md)  
[3. Android implementation details (Work in progress)](README.md)  
[4. Possible enhancements and improvements (Work in progress)](README.md)  

[Sample app](./WASampleApp)  

# Introduction

Hello everybody!

I develop Android applications professionally since 2011. Over the years I've elaborated a set of architectural techniques and approaches, which I use in almost every app I develop, and which make my code stable and agile. Now I'd like to share these techniques with you.

I've called it "Wooden architecture" - because I find it as straightforward and reliable as rude wooden houses construction. Most probably you're not going to build a skyscraper with woods, but it is pretty enough for any application of small and medium size.

Wooden core concepts can be used not only for Android applications, but theoretically for any platform. Wooden architecture was originally based on a server-side backend architecture I was participating in, later was successfully used in crossplatform iOS/Android applications, Windows Desktop application and even in a small PHP server. But surely this guide is speaking mostly about Android.

The guide is accompanied with a [sample app](./WASampleApp) code. While reading the guide you can refer to sample code to see how the approaches described work in practice. The app is a simple weather forecast application: you enter a city name and receive a weather forecast. Forecasts downloaded are cached in a local database for some time. Application also shows a marker if the weather is good enough to go outside for some running: what is "good enough" is defined by user via "Settings" screen.

Application uses [OpenWeatherMap API](https://openweathermap.org/api) to get a forecast. If one day you'll find this API down, or you will experience network issues, or you'd like to perform some tests, a "Fake API" is also provided - see [WASampleApp/d_container/src/main/java/ru/pvolan/container/Container.java](./WASampleApp/d_container/src/main/java/ru/pvolan/container/Container.java) to switch on it.

Disclaimer. _A good architecture comes from understanding it more as a journey than as a destination. Robert C. Martin._ Although this guide is quite practical and detailed, I'm not going to bring you to some "once and for all" solution. I'm just showing you the path I've personally walked during my journey. It is your choice to follow me or find your own way. Some implementation details may require adjustment to your particular project.  

[Okay, let's go inside!](./Chapter1/index.md)  
