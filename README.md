# kafka-producer-service
kafka-producer-service
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Kafka Producer Service – Order Lifecycle</title>
  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      line-height: 1.6;
      color: #1f2937;
      max-width: 900px;
      margin: auto;
      padding: 20px;
    }
    h1, h2, h3 { color: #111827; }
    code {
      background: #f3f4f6;
      padding: 3px 6px;
      border-radius: 4px;
      font-size: 0.95em;
    }
    pre {
      background: #111827;
      color: #f9fafb;
      padding: 15px;
      border-radius: 6px;
      overflow-x: auto;
    }
    ul { margin-left: 20px; }
    .box {
      background: #f9fafb;
      border-left: 4px solid #16a34a;
      padding: 12px 16px;
      margin: 16px 0;
    }
    .highlight {
      background: #fff7ed;
      border-left: 4px solid #f97316;
      padding: 12px 16px;
      margin: 16px 0;
    }
  </style>
</head>

<body>

<h1>Kafka Producer Service – Order Lifecycle Publisher</h1>

<p>
This project is a <strong>production-style Kafka producer</strong> built with
<strong>Spring Boot</strong> and <strong>Apache Kafka</strong>.  
It exposes REST APIs to publish <strong>Order Lifecycle Events</strong> into Kafka,
simulating real-world event-driven microservice behavior.
</p>

<div class="box">
<strong>Order Lifecycle:</strong><br/>
CREATED → CONFIRMED → SHIPPED
</div>

<hr/>

<h2>Architecture Overview</h2>

<pre>
Client (Postman / UI / Service)
        |
        v
Kafka Producer Service (REST APIs)
        |
        v
Kafka Topic: order-events
        |
        v
Kafka Consumer Service(s)
   - Validation
   - Manual Ack
   - Retry Topics
   - Dead Letter Topic (DLT)
   - Offset Replay
</pre>

<hr/>

<h2>Technology Stack</h2>
<ul>
  <li>Java 17</li>
  <li>Spring Boot</li>
  <li>Spring Kafka</li>
  <li>Apache Kafka (Docker)</li>
  <li>Maven</li>
</ul>

<hr/>

<h2>Event Contract</h2>

<p>
The producer sends a JSON payload representing an order lifecycle event:
</p>

<pre>
{
  "orderId": "O-10008",
  "type": "CREATED | CONFIRMED | SHIPPED",
  "quantity": 2,
  "timestamp": "2026-02-03T18:00:00Z"
}
</pre>

<hr/>

<h2>Kafka Configuration</h2>

<ul>
  <li><code>bootstrap.servers</code>: Kafka broker (Docker)</li>
  <li><code>topic</code>: <code>order-events</code></li>
  <li>Producer serializer: <code>JsonSerializer</code></li>
  <li>Key serializer: <code>StringSerializer</code> (orderId as key)</li>
</ul>

<div class="highlight">
Using <strong>orderId as the message key</strong> enables stable partitioning, which is a common production practice
for maintaining ordering per entity (e.g., per order).
</div>

<hr/>

<h2>REST APIs</h2>

<p>
This service exposes dedicated endpoints for the order lifecycle:
</p>

<ul>
  <li><code>POST /orders/created</code></li>
  <li><code>POST /orders/confirmed</code></li>
  <li><code>POST /orders/shipped</code></li>
</ul>

<p><strong>Example request body:</strong></p>

<pre>
{
  "orderId": "O-10008",
  "quantity": 2,
  "timestamp": "2026-02-03T18:00:00Z"
}
</pre>

<p>
Each endpoint enriches the payload with the correct <code>type</code> and publishes the message to Kafka.
</p>

<hr/>

<h2>Message Publishing Flow</h2>

<pre>
POST /orders/created
 → build OrderEvent(type=CREATED)
 → kafkaTemplate.send("order-events", orderId, event)
 → return HTTP 200/201 response
</pre>

<p>
Publishing includes:
</p>
<ul>
  <li>Structured event format (JSON)</li>
  <li>Explicit event type per endpoint</li>
  <li>OrderId as Kafka key for partitioning</li>
</ul>

<hr/>

<h2>How to Run (Local + Docker Kafka)</h2>

<h3>1) Start Kafka</h3>
<pre>
docker compose up -d
</pre>

<h3>2) Verify Kafka is running</h3>
<pre>
docker ps
</pre>

<h3>3) List topics</h3>
<pre>
docker exec -it kafka-producer-service-kafka-1 \
  kafka-topics --bootstrap-server localhost:9092 --list
</pre>

<h3>4) Run Producer Service</h3>
<pre>
mvn spring-boot:run
</pre>

<hr/>

<h2>Testing with Postman</h2>

<p>
Use Postman to publish lifecycle events:
</p>

<ul>
  <li>Send a CREATED event first</li>
  <li>Then CONFIRMED</li>
  <li>Then SHIPPED</li>
</ul>

<p>
You can verify the output via consumer logs or using Kafka console consumer:
</p>

<pre>
docker exec -it kafka-producer-service-kafka-1 \
  kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --from-beginning \
  --property print.key=true \
  --property key.separator=" | "
</pre>

<hr/>

<h2>What This Project Demonstrates</h2>

<ul>
  <li>REST → Kafka event publishing pattern</li>
  <li>Clean event contract for lifecycle events</li>
  <li>Kafka key-based partitioning strategy</li>
  <li>Dockerized Kafka local development workflow</li>
  <li>Integration readiness for downstream consumers (retry/DLT/replay)</li>
</ul>

<hr/>

</body>
</html>