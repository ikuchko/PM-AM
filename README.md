# PMAM

##### Epicodus java team week project using Java, Spark and Postgres

#####

## Description

PM:AM is a project management web app written in Java and implemented in Spark framework. The intent of this project is to break up larger projects into smaller parts (Epics, Stories, and Tasks respectively), and allow different permissions for project managers and developers. Such functionality includes assigning and completing tasks, viewing the number of tasks and stories on a particular project-- as well as how many developers are assigned to these tasks-- and seeing the efficiency of developers by tracking time completion on assigned tasks.

## Setup

Clone this repository:
```
$ cd ~/Desktop
$ git clone https://github.com/ikuchko/PM-AM.git
$ cd hair_salon
```

Open terminal and run Postgres:
```
$ postgres
```

Open a new tab in terminal and create the `project_tracking` database:
```
$ psql
$ CREATE DATABASE project_tracking;
$ psql project_tracking < project_tracking.sql
```

Navigate back to the directory where this repository has been cloned and run gradle:
```
$ gradle run
```

##Alternative running

Follow this link:
```
http://thawing-brook-29610.herokuapp.com/
```

## Legal

Copyright (c) 2015 Illia Kuchko

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
