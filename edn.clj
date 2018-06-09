(ns iris-daemon-tools.edn)

(defn ->mongo-id
  "Convert an object to a bson id. Accepts hex strings and byte arrays, idempotent."
  [o]
  (case (type o)
    org.bson.types.ObjectId o
    String (org.bson.types.ObjectId. o)
    (Class/forName "[B") (org.bson.types.ObjectId. o)
    (org.bson.types.ObjectId. (str o))))

(defn read-mongo-objectid
  "Form to Mongo ObjectId. This is for embedding ObjectId instances in EDN."
  [f]
  `(->mongo-id ~f))

(defmethod print-method org.bson.types.ObjectId
  [o w]
  (print-simple (str "#mongo/objectid \""o"\"") w))

(defn read-joda-datetime
  "String to Joda DateTime. This is for embedding DateTime instances in EDN."
  [str]
  `(org.joda.time.DateTime. ~str))

(defmethod print-method org.joda.time.DateTime
  [o w]
  (print-simple (str "#joda/datetime \""o"\"") w))
