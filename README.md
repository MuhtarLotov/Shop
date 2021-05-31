<h3>Requirements</h3>
* Java 11

***

<h3>Include WSO3 as service in Linux based system</h3>

Locate to _system_ folder:<br>
<code>cd /etc/systemd/system</code><br>

Create file _wso3.service_:<br>
```
[Unit]
Description=WSO3

[Service]
WorkingDirectory={location_of_jar_file}
ExecStart=java -jar {location_of_jar_file}/wso3-1.0.0.jar
Restart=always

[Install]
WantedBy=multi-user.target
```

Start and enable WSO3 service:<br>
```
sudo systemctl daemon-reload
sudo systemctl start wso3.service
sudo systemctl status wso3.service
sudo systemctl enable wso3.service
```

***

[WSO3 Postman collection](/WSO3.postman_collection.json)