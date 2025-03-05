#!/usr/bin/env bash

# 所有的密码都是 "123456"

openssl req -newkey rsa:2048 -x509 -keyout key.pem -out cert.pem
openssl pkcs12 -export -in cert.pem -inkey key.pem -out certificate.p12 -name "certificate"
keytool -importkeystore -srckeystore certificate.p12 -srcstoretype pkcs12 -destkeystore certificate.jks

exit 0
