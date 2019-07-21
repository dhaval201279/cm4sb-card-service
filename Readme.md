# Source code of my [blog]() on how to perform [Chaos Engineering](https://principlesofchaos.org/) using
1. [Chaos Monkey for Spring Boot](https://github.com/codecentric/chaos-monkey-spring-boot)
2. [Consul](https://www.consul.io/)
3. [Prometheus](https://prometheus.io/)
4. [Grafana](https://grafana.com/)

#### Card Service APIs
Comprises of business flow which allows consumer of this application to view card based on Id or fetch all the cards

Method	| Path	| Description
------------- | ------------------------- | ------------- :
GET	| /card/{cardId}	| Gets Card based on card identifier	
GET	| /cards	| Fetches all the cards stored in database

## How to enable monitoring of demo application

### Start Consul
1. Go to home of Consul e.g. "C:\consul.io"
2. Run below command in powershell
    > .\consul.exe agent -dev -node machine
3. Check whether Consul is up and running by accessing URL (http://localhost:8500/ui/dc1/services    
) in browser
     
### Start Prometheus
1. Go to home of Prometheus e.g. "C:\prometheus-2.2.1.windows-amd64"
2. Run below command in powershell
    > .\prometheus.exe --config.file=prometheus.yml
3. Check whether Prometheus is up and running by accessing URL (http://localhost:9090/service-discovery) in browser    
    
### Start Grafana
1. Go to home of Consul e.g. C:\grafana-5.1.3\bin
2. Run below command in powershell
    > .\grafana-server.exe agent -dev -node machine
3. Check whether Grafana is up and running by accessing URL (http://localhost:7070) in browser

## How to do load testing using [Apache Bench](https://httpd.apache.org/docs/2.4/programs/ab.html)
1. Go to Apache Bench's bin directory e.g. C:\Apache24\bin
2. Run below command in powershell to invoke Fetch Cards API 250 times with 4 concurrent threads
    > .\ab.exe -n 250 -c 4 http://localhost:8090/cards          