# alertmanager-dingtalk-webhook
![报警机器人](https://img-blog.csdnimg.cn/639b09430f6a477caae80f07078b182d.png)
## 使用Docker运行

 ```
 $ docker run -itd -p 8080:8080 -e dingTalk_assessToken=<钉钉机器人TOKEN> -e dingTalk_secret=<钉钉机器人安全SECRET>  admin4j/alertmanager-dingtalk-webhook:latest
 ```
### 环境变量配置：

- dingtalk_assessToken：钉钉机器人 TOKEN
- dingTalk_secret： 钉钉机器人 secret
### 添加报警机器人
![添加报警机器人](https://img-blog.csdnimg.cn/55135f763d994958a8cceb905a6a3ad5.png)

## 使用k8s运行
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: alertmanager-dingtalk-webhook
spec:
  replicas: 1
  selector:
    matchLabels:
      app: alertmanager-dingtalk-webhook
  template:
    metadata:
      labels:
        app: alertmanager-dingtalk-webhook
    spec:
      affinity:
        # 反亲和性调度 使各个pod不在同个node里
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - weight: 100
              podAffinityTerm:
                topologyKey: kubernetes.io/hostname
                labelSelector:
                  matchExpressions:
                    - key: app
                      operator: In
                      values:
                        - alertmanager-dingtalk-webhook
      containers:
        - name: alertmanager-dingtalk-webhook
          image: admin4j/alertmanager-dingtalk-webhook:latest
          imagePullPolicy: Always
          resources:
            limits:
              memory: 1G
            requests:
              memory: 1G
          livenessProbe:
            httpGet:
              path: /actuator/health
              port: 8080
              scheme: HTTP
            initialDelaySeconds: 60
            timeoutSeconds: 10
            periodSeconds: 120
          readinessProbe:
            httpGet:
              path: /actuator/health
              port: 8080
              scheme: HTTP
            initialDelaySeconds: 10
            timeoutSeconds: 10
            periodSeconds: 120
          env:
            - name: dingTalk_assessToken
              valueFrom:
                configMapKeyRef:
                  name: alertmanager-webhook-cm
                  key: dingTalk_assessToken
            - name: dingTalk_secret
              valueFrom:
                configMapKeyRef:
                  name: alertmanager-webhook-cm
                  key: dingTalk_secret
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: alertmanager-webhook-cm
data:
  dingTalk_assessToken: d54bd0d41decb444d8e716fa661c03233d10f75559a0041c60148a1de8bb8028
  dingTalk_secret: SEC7f5baedc3bc637942041077a6e5aeb4275cf403ad3c057e75b0ed8921dc26625

----
kind: Service
apiVersion: v1
metadata:
  name: alertmanager-webhook-service
spec:
  ports:
    - protocol: TCP
      port: 8080
      targetPort: 8080
  selector:
    app: alertmanager-dingtalk-webhook
  type: ClusterIP
  sessionAffinity: None
```
## 配置AlertManager 
```
global:
  resolve_timeout: 5m
route:
  group_by: ['job', 'severity']
  group_wait: 30s
  group_interval: 5m
  repeat_interval: 12h
  receiver: default
receivers:
  - name: 'default'
    webhook_configs:
        - url: 'http://alertmanager-webhook-service.monitoring:8080/AlertManager'
```
# 参考文档
- [钉钉自定义机器人文档](https://open.dingtalk.com/document/org/application-types)
- [【Prometheus】Alertmanager告警全方位讲解](https://andyoung.blog.csdn.net/article/details/126243110)
