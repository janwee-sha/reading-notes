# 用Kubeadm和Kubectl启动一个Kubernetes集群

> Refernce link: https://www.howtogeek.com/devops/how-to-start-a-kubernetes-cluster-from-scratch-with-kubeadm-and-kubectl/

- Kubeadm – 一个在集群层面上操作的管理工具。你将用它来创建你的集群并添加额外的节点。
- Kubectl – 与Kubernetes集群互动的CLI。
- Kubelet – 这是在集群的工作节点上运行的Kubernetes进程。它负责与控制平面保持联系，并在请求时启动新的容器。

## 创建集群

```
$ sudo kubeadm init --pod-network-cidr=10.244.0.0/16
...
[control-plane] Creating static Pod manifest for "kube-apiserver"
[control-plane] Creating static Pod manifest for "kube-controller-manager"
[control-plane] Creating static Pod manifest for "kube-scheduler"
[etcd] Creating static Pod manifest for local etcd in "/etc/kubernetes/manifests"
...
Your Kubernetes control-plane has initialized successfully!
...
```

## 准备Kubeconfig文件

首先，将自动生成的Kubeconfig文件复制到你自己的.kube/config目录。将该文件的所有权调整为自己，以便Kubectl能够正确地读取其内容。

```
$ mkdir -p $HOME/.kube
$ sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
$ sudo chown $(id -u):$(id -g) $HOME/.kube/config
```

## 安装一个Pod网络插件

Kubernetes 要求集群中存在 Pod 网络插件以使工作节点正常运行。您必须手动安装兼容的插件才能完成安装。Calico 和 Flannel 是最受欢迎的两种选择。

使用Kubectl将Flannel添加到你的集群中：

```
$ kubectl apply -f https://raw.githubusercontent.com/flannel-io/flannel/master/Documentation/kube-flannel.yml
```

等待片刻，然后在你的终端运行`kubectl get nodes`。你应该看到你的节点显示为就绪，你可以开始与你的集群进行交互：

```
$ kubectl get nodes
NAME              STATUS   ROLES           AGE   VERSION
racknerd-9e9606   Ready    control-plane   11m   v1.26.2
```

如果你运行`kubectl get pods --all-namespaces`，你应该看到控制平面组件、CoreDNS和Flannel都已启动并运行。

```
$ kubectl get pods --all-namespaces
NAMESPACE      NAME                                      READY   STATUS    RESTARTS   AGE
kube-flannel   kube-flannel-ds-l6kws                     1/1     Running   0          3m40s
kube-system    coredns-787d4945fb-ppj72                  1/1     Running   0          14m
kube-system    coredns-787d4945fb-rgkpj                  1/1     Running   0          14m
kube-system    etcd-racknerd-9e9606                      1/1     Running   0          14m
kube-system    kube-apiserver-racknerd-9e9606            1/1     Running   0          14m
kube-system    kube-controller-manager-racknerd-9e9606   1/1     Running   0          14m
kube-system    kube-proxy-296cp                          1/1     Running   0          14m
kube-system    kube-scheduler-racknerd-9e9606            1/1     Running   0          14m
```

## 与集群交互

现在您可以开始使用 Kubectl 与集群交互。 先删除控制面板节点上的默认污点，以允许 Pod 调度到它上面。 Kubernetes 阻止 Pod 运行在控制面板节点上以避免资源争用，但这种限制对于本地使用是不必要的。

```
$ kubectl taint node racknerd-9e9606 node-role.kubernetes.io/control-plane:NoSchedule-
node/racknerd-9e9606 untainted
```

现在试着启动一个简单的NGINX Pod：

```
$ kubectl run nginx --image nginx:latest
pod/nginx created
```

使用一个NodePort服务暴露它：

```
$ kubectl expose pod/nginx --port 80 --type NodePort
service/nginx exposed
```

查看服务：

```
$ kubectl get services
NAME         TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        32m
nginx        NodePort    10.96.213.56   <none>        80:32222/TCP   95s
```

发送HTTP请求至nginx服务的端点：

```
$ curl http://10.96.213.56:80
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
...
```