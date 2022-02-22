# text.frittenburger.de
WebService for analysing text

![text.frittenburger.de](webservice.png)

# Quick Start
```
mvn package
java -jar target/NLP-Test-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
```



## Use docker (http://localhost)
```
docker run -it --rm -p 80:4567 frittenburger/text
```

## use curl
```
curl -k -X POST -H "Authorization: Bearer limited-guest-access" --data-urlencode  'language=spanish' --data-urlencode  'text=Yo vivo con Maria en Barcelona.' https://text.frittenburger.de/parse
```

Use URl-Encoding
```
curl -k -X POST -H "Authorization: Bearer limited-guest-access" --data-ascii "language=spanish&text=%C2%BFD%C3%B3nde+est%C3%A1+el+coche%3F"  https://text.frittenburger.de/parse
``` 

```
curl -k -X POST -H "Authorization: Bearer limited-guest-access" --data-urlencode  'language=spanish' --data-urlencode  "text=$(echo -n "¿Dónde está el coche?" | iconv -f ISO-8859-1 -t UTF-8)" 
https://text.frittenburger.de/parse
```

## Use python
```
python parse.py --language spanish --text "¿Dónde está el coche?"
```

# Contact
Dirk Friedenberger, Waldaschaff, Germany

Write me (oder Schreibe mir)
projekte@frittenburger.de

http://www.frittenburger.de 

