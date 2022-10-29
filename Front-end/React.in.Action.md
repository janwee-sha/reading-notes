# 第1章 初识React

## 1.1 初识React

React是一个用于构建跨平台用户界面的JavaScript库。
React的思维模型广泛适用了函数式和面向对象编程的概念，并重点将组建作为构建的主要单元。

- **组件**——封装的功能单元，他是React的主要单元。它们利用数据（**属性**和**状态**）将UI渲染为输出。
- **React库**——React使用一组核心库。React库的核心与react-dom和react-native紧密配合，其侧重于组件规范和定义。
- **第三方库**——React并不自带数据建模、HTTP调用、样式库或其他前端应用的常见工具。
- **运行React应用**——React应用运行在开发人员位置构建应用的平台上。本书关注的是Web平台并构建了一个基于浏览器和服务器的应用，而其他诸如React Native和React VR这样的项目则创造了应用在其他平台上运行的可能性。

## 1.3 虚拟DOM

React的虚拟DOM是模仿或镜像存在于浏览器中的文档对象模型的数据结构或数据结构的集合。通常，虚拟DOM会作为应用程序和浏览器DOM之间的中间层。虚拟DOM像开发人员隐藏了变更检测与管理的复杂性并将其转移到专门的抽象层。

图1.3展示了DOM和虚拟DOM之间的关系。

![image](https://github.com/janwee-sha/reading-notes/blob/main/Back-end/React.in.Action.Graph.1-3.png)

### 1.3.1 DOM

DOM或文档对象模型是一个允许JavaScript与不同类型的文档（HTML、XML或SVG）进行交互的编程接口。它有标准驱动的规范，这意味着公共工作组已经建立了它应该具有的标准特性集以及行为方式。虽然存在其他实现，但是DOM几乎已经是Chrome、Firefox和Edge等Web浏览器的代名词了。

DOM提供过了访问、存储和操纵文档不同部分的结构化方式。从较高层面来讲，DOM是一种反映了XML文档层次结构的树形结构。这个数结构由子树组成，子树由节点组成。这些是组成Web页面和应用的div和其他元素。

你在JavaScript中使用的方法不全是JavaScript语言本身的一部分（document.findElementById、querySelectorAll、alert等）。它们是更大的Web API集合（浏览器中的DOM和其他API）的一部分。