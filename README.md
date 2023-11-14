# How to Run and Test Project
To run, simply execute the `mvnw` (or `mvnw.cmd` if you're on windows) executable like so:
```
./mvnw spring-boot:run
```
or
```
./mvnw.cmd spring-boot:run
```

To test, you could send requests to the api using `curl` or `httpie` as so:
```
curl -X POST -H "Content-Type: application/json" -d '{"input": "<s>"}' localhost:8080/api/<p> -w "\n"
```
or
```
echo '{"input": "<s>"}' | http POST localhost:8080/api/<p>
```
where `<s>` is the input string you'd like to test and `<p>` is the part you are testing, either `part1` or `part2`.

You could also test my project by adding to the testcases I wrote, provided in `src/test/java/dev/banisomo/elmersWebAPI`.
