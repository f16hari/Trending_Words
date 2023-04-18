zookeeper-server-start /opt/homebrew/etc/kafka/zookeeper.properties

kafka-server-start /opt/homebrew/etc/kafka/server.properties

kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic foobar

kafka-console-producer --broker-list localhost:9092 --topic foobar

kafka-console-consumer --bootstrap-server localhost:9092 --topic foobar --from-beginning