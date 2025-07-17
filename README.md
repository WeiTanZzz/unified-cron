# 🕒 Distributed Cron Scheduler (Kafka + Kubernetes)

**Minimal Architecture Demonstration** of a horizontally scalable distributed cron system powered by **Kafka** and **Kubernetes**.

---

## ⚙️ What This Is

This project showcases the **core building blocks** for building a distributed cron system that can:

- Trigger jobs via **Kafka events**
- Dynamically launch **Kubernetes Pods** to execute tasks
- Scale horizontally using Kafka consumer groups

> ❗ **This is NOT a production-ready framework**  
> It is a **minimal working prototype** that **omits advanced concerns** such as:
>
> - Persistent job scheduling storage
> - Job retries or deduplication
> - Access control or multi-tenant policies
> - Lifecycle tracking or notifications

---

## 🏗️ High-Level Architecture
<img width="1150" height="650" alt="image" src="https://github.com/user-attachments/assets/669ff857-a80b-4698-bf8f-884504e40df1" />

## 🔄 Predefined Runtimes Support

This architecture supports multiple **predefined execution environments**, each mapped to a dedicated container image or Pod configuration.  
It allows different types of jobs to be executed in fully isolated and appropriately provisioned containers.

See [runtimes](https://github.com/WeiTanZzz/unified-cron/tree/main/runtimes) to get more.
