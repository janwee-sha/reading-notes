> The following content is a reference from Mark Tielens Thomas's [*React in Action*](https://www.oreilly.com/library/view/react-in-action/9781617293856/).

# Chapter I. Meet React

## 1.1 Meet React

React是一个用于构建跨平台用户界面的JavaScript库。
React的思维模型广泛适用了函数式和面向对象编程的概念，并重点将组建作为构建的主要单元。

- **组件**——封装的功能单元，他是React的主要单元。它们利用数据（**属性**和**状态**）将UI渲染为输出。
- **React库**——React使用一组核心库。React库的核心与react-dom和react-native紧密配合，其侧重于组件规范和定义。
- **第三方库**——React并不自带数据建模、HTTP调用、样式库或其他前端应用的常见工具。
- **运行React应用**——React应用运行在开发人员位置构建应用的平台上。本书关注的是Web平台并构建了一个基于浏览器和服务器的应用，而其他诸如React Native和React VR这样的项目则创造了应用在其他平台上运行的可能性。

## 1.3 The Virtual DOM

React的虚拟DOM是模仿或镜像存在于浏览器中的文档对象模型的数据结构或数据结构的集合。通常，虚拟DOM会作为应用程序和浏览器DOM之间的中间层。虚拟DOM像开发人员隐藏了变更检测与管理的复杂性并将其转移到专门的抽象层。

图1.3展示了DOM和虚拟DOM之间的关系。

![image](https://github.com/janwee-sha/reading-notes/blob/main/Front-end/React.in.Action.Graph.1-3.png)

### 1.3.1 The DOM

DOM或文档对象模型是一个允许JavaScript与不同类型的文档（HTML、XML或SVG）进行交互的编程接口。它有标准驱动的规范，这意味着公共工作组已经建立了它应该具有的标准特性集以及行为方式。虽然存在其他实现，但是DOM几乎已经是Chrome、Firefox和Edge等Web浏览器的代名词了。

DOM提供过了访问、存储和操纵文档不同部分的结构化方式。从较高层面来讲，DOM是一种反映了XML文档层次结构的树形结构。这个数结构由子树组成，子树由节点组成。这些是组成Web页面和应用的div和其他元素。

你在JavaScript中使用的方法不全是JavaScript语言本身的一部分（document.findElementById、querySelectorAll、alert等）。它们是更大的Web API集合（浏览器中的DOM和其他API）的一部分。

与DOM交互通常很简单，但在大型Web应用中可能会变得复杂。幸运的是，当使用React构建应用时我们通常不需要直接与DOM交互——我们基本上把它都交给了React。

![image](https://github.com/janwee-sha/reading-notes/blob/main/Front-end/React.in.Action.Graph.1-4.png)

### 1.3.2 The virtual DOM

性能是React涉及和实现的另一个关键因素。实现虚拟DOM有助于解决这个问题。React的虚拟DOM更为重要的是提供了健壮的API、简单的思维模型和注入跨浏览器兼容性等其他特性，而不是对性能的极端关注。

## 1.4 COMPONENTS：THE FUNDAMENTAL UNIT OF REACT

### 1.4.1 Components in general

![image](https://github.com/janwee-sha/reading-notes/blob/main/Front-end/React.in.Action.Graph.1-6.png)

图1-6 一个界面被拆解为组件的例子。

### 1.4.2 Components in React: Encapsulated and reusable

React组件具有良好的封装性、复用性和组合性。

# Chapter 2 <Hello World/>：our first component

The following figure gives you an overview of the core aspects of most React applications.

![image](https://note.youdao.com/favicon.ico)

- **Components**. Encapsulated units of functionality that are the primary unit in React. They utilize data (properties and state) to render your UI as output. Certain types of React components also provide a set of lifecycle methods that you can hook into. The rendering process (outputting and updating a UI based on your data) is predictable in React, and your components can hook into it using React's APIs.
- **React libraries**. React uses a set of core libraries. The core React libraryworks with the *react-dom* and *react-native* libraries and is focused on component specification and definition. It allows you to build a tree of components that a renderer for the browser or another platform can use. *react-dom* is one such renderer and is aimed at browser environments and server-side rendering. The React Native libraries focus on native platforms and let you create React applications for iOS, Android, and other platforms.
- **Third-party libraries**. React doesn't come with tools for data modeling. HTTP calls, styling libraries, or other common aspects of a front-end applications. This leaves you free to use additional code, or other tools you prefer in your application.
- **Running a React application**. Your React application, created from components, runs on a platform of your choice: web, mobile, or native.

## 2.1 INTRODUCING REACT COMPONENTS

### 2.2.1 多组合：组合关系和父子关系

React的组件被组织成一个树形结构。React的组件像DOM元素一样，也可以嵌套而且能够包含其他组件。它们也可以与其他组件出现在相同的层级上。

## 2.2 用React创建组件

React.createElement被用来创建React元素，它的函数签名如下：

```
React.createElement(
    String/ReactClass type,
    [object props],
    [children...]
) -> React Element
```

`React.createElement`接收字符串或组件（要么是扩展了`React.Component`的类，要么是一个函数）、属性（`props`）对象和子元素集合（`children`）并返回一个React元素。

- `type` —— 可以传入一个表示要创建的HTML元素标签名的字符串（“`div`”、“`span`”、“`a`”等）或一个React类。
- `props` —— props对象提供了一种方法，指定HTML元素上会定义哪些属性（如果是在ReactDOMElement的上下文中）或组件类的实例可以使用哪些属性。
- `children` —— 使用children组合其他React组件。




