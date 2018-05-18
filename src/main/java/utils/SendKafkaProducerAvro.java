//package utils;
//
//import com.hortonworks.registries.schemaregistry.serdes.avro.AvroSnapshotSerializer;
//import com.hortonworks.registries.schemaregistry.serdes.avro.SerDesProtocolHandlerRegistry;
//import com.hortonworks.registries.schemaregistry.serdes.avro.kafka.KafkaAvroDeserializer;
//import com.hortonworks.registries.schemaregistry.serdes.avro.kafka.KafkaAvroSerializer;
//import org.apache.avro.Schema;
//import org.apache.avro.generic.GenericData;
//import org.apache.avro.generic.GenericRecord;
//import org.apache.commons.io.IOUtils;
//import org.apache.kafka.clients.producer.*;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
///**
// * @jiguiyang
// *
// * send avro data to kafka
// */
//public class SendKafkaProducerAvro {
//
//    private static final String KAFKASERVERS = "172.16.236.91:6667";
//    private static final String KAFKASCHEMAURL = "http://172.16.236.91:7788/api/v1";
//    private Properties props;
//    private KafkaProducer<String, Object> kafkaProducer;
//
//    public SendKafkaProducerAvro() {
//        props = new Properties();
//        props.put("bootstrap.servers", KAFKASERVERS);
//        props.put("request.required.acks", "1");
//        props.put("schema.registry.url", KAFKASCHEMAURL);
//        props.put("key.serializer", KafkaAvroSerializer.class.getName());
//        props.put("value.serializer", KafkaAvroSerializer.class.getName());
//        props.put("value.deserializer", KafkaAvroDeserializer.class.getName());
//        props.put("key.deserializer", KafkaAvroDeserializer.class.getName());
//        props.put("group.id", "1");
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 65536);
//        props.put(AvroSnapshotSerializer.SERDES_PROTOCOL_VERSION, SerDesProtocolHandlerRegistry.METADATA_ID_VERSION_PROTOCOL);
//    }
//
//    public void SendToKafka(String topic,String schemaFileName,EntityConvertSchemaMapper convertSchemaMapper,Object object,Callback callback){
//        try {
//            kafkaProducer = new KafkaProducer<String, Object>(props);
//            Schema schema = new Schema.Parser().parse(getSchema(schemaFileName));
//            GenericRecord avroRecord = new GenericData.Record(schema);
//            convertSchemaMapper.addToGenericRecordBySchema(avroRecord, object);
//            ProducerRecord<String, Object> data = new ProducerRecord<String, Object>(topic, avroRecord);
//            kafkaProducer.send(data, callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private String getSchema(String schemaFileName) throws IOException {
//
//        InputStream schemaResourceStream = this.getClass().getResourceAsStream(schemaFileName);
//        if (schemaResourceStream == null) {
//            throw new IllegalArgumentException("Given schema file [" + schemaFileName + "] does not exist");
//        }
//        String schemaText = IOUtils.toString(schemaResourceStream, "UTF-8");
//        return schemaText;
//    }
//
//
//    interface EntityConvertSchemaMapper{
//
//        void addToGenericRecordBySchema(GenericRecord avroRecord, Object object);
//    }
//}
