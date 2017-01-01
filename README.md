# CREAM - A simple banking app
_"Cash Rules Everything Around Me" - Method Man_


A few years ago I was fed up of how useless all the banks were at helping me understand my transactions/spending patterns with their online banking. Basically, they all just seemed to let you log on, and then see a list of transactions (often just referencing codes) of payments in and out by date. Nothing else. It seemed like there were lots of simple things missing that could make it a lot more useful, to name a few:

* Smarter tagging/categorisation - where some direct debit was just listed by the code of the recipient rather than meaningful info, or being able to group all the spending in coffee shops
* Alerting to changes/unexpected behaviour - for example when a fixed term-price contract comes to an end and the price changes - detecting a change in a regular fixed aount/recipient should be easy


So this is what I started building - a lot of the functionality has since been built into banks such as Mondo, the aim of this webapp was simply to allow you to import transaction histories (most online banks I use provide the ability to export transactions in csv) so you could view/filter/tag any existing bank data. It only supports Santander at the moment, and the alerting stuff never actually got built in, but I thought I would chuck it on Github rather than have it just sit around gathering dust in a private repo.


## Building & Running 
Its a simple java, maven app (it was several years ago, so not migrated to Spring Boot, Gradle, Groovy etc) - and it builds a WAR file. It also uses LESS for style stuff, which is also built by maven (although watch out for silent failures if you make changes!). If you are using an eclipse based IDE you can follow the guide here: to get incremental build support for LESS building (e.g. just change the LESS source and go refresh in the browser and it will reload).

You can run the WAR file in any tomcat/IDE server, and the backend is currently expecting a MySql DB (but can easily be switched for a differed DB driver if required)


## Roadmap
* Migrate to Spring Boot, Groovy, Gradle
* Add further bank transaction importers
* Add alerting framework


## Screenshots
(I loaded in a generated transaction history - which is why some of the charts look a little weird)
![alt text](https://github.com/robhinds/cream/blob/master/cream_login.png "Welcome page and login")
![alt text](https://github.com/robhinds/cream/blob/master/cream_dashboards.png "Welcome page and login")
![alt text](https://github.com/robhinds/cream/blob/master/cream_transactions.png "Welcome page and login")
