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

![image](https://github.com/janwee-sha/reading-notes/blob/main/Front-end/React.in.Action.Graph.2-1.png)

- **Components**. Encapsulated units of functionality that are the primary unit in React. They utilize data (properties and state) to render your UI as output. Certain types of React components also provide a set of lifecycle methods that you can hook into. The rendering process (outputting and updating a UI based on your data) is predictable in React, and your components can hook into it using React's APIs.
- **React libraries**. React uses a set of core libraries. The core React libraryworks with the *react-dom* and *react-native* libraries and is focused on component specification and definition. It allows you to build a tree of components that a renderer for the browser or another platform can use. *react-dom* is one such renderer and is aimed at browser environments and server-side rendering. The React Native libraries focus on native platforms and let you create React applications for iOS, Android, and other platforms.
- **Third-party libraries**. React doesn't come with tools for data modeling. HTTP calls, styling libraries, or other common aspects of a front-end applications. This leaves you free to use additional code, or other tools you prefer in your application.
- **Running a React application**. Your React application, created from components, runs on a platform of your choice: web, mobile, or native.

## 2.1 INTRODUCING REACT COMPONENTS

### 2.2.1 Multiple components: COmposition and parent-child relationships

React的组件被组织成一个树形结构。React的组件像DOM元素一样，也可以嵌套而且能够包含其他组件。它们也可以与其他组件出现在相同的层级上。

## 2.2 Creating components in React

### 2.2.1 Creating React elements

> Definition： React元素是React中轻量的、无状态的、不可变的基础类型。React元素有ReactComponentElement和ReactDOMElement两种类型。ReactDOMElement是DOM元素的虚拟表示。ReactComponentElement引用了对应着React组件的一个函数或类。

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

### 2.2.3 Creating React components

两种基本类型的组件：

- 无状态函数组件。
- 使用JavaScript类创建的有状态的React组件。

### 2.2.4 Creating React classes

React中的组件是帮助将React元素和函数组织到一起的类。它们可以创建为扩展自React.Component基类的类或是函数。

创建React的类的方式：

```
class MyReactClassComponent extends Component {
    render() {}
}
```

创建React类的老办法是使用createClass方法。这种方式随着JavaScript的类的到来而发生了改变。

### 2.2.5 The render method

几乎任何向屏幕显示内容的组件都带有render方法。

render方法只返回一个React元素。这与React元素的创建方法相似——它们可以嵌套但最高层只有一个节点。

React为使用JavaScript类创建的有状态的React组件创建了一个“支撑实例”。

React会为React类的实例创建并追踪一个特殊的数据对象，这个对象随时间保持存在并可以通过特定的React函数进行更新。

![image](https://github.com/janwee-sha/reading-notes/blob/main/Front-end/React.in.Action.Graph.2-10.png)

支撑实例是一种为特定组件提供数据存储和访问的方法。存储在该实例中的数据会通过特定的API方法被提供给组件的render方法。

### 2.2.6 Property validation via PropTypes

React类组件可以自由使用自定义属性。

可以使用来自React命名空间PropTypes的验证器验证所使用的属性一遍能够防止并规划组件使用的数据种类。

这组PropTypes验证器过去包含在React核心库中，但之后在React 15.5版本中被分离出去并被废弃了。要使用PropTypes，需要安装prop-types软件包。

prop-types库提供了一组校验器，它们可以指定组件需要或期望什么样的属性。

使用PropTypes时，需要通过类的静态属性或通过类定义后的简单属性赋值来把propTypes属性添加到React.Component类。

## 2.3 The life and times of a component

放大React的渲染过程。React使用React类和React元素创建内存中控制实际DOM的虚拟DOM。它还创建了一个“综合”事件系统，以便仍可以对来自浏览器的事件做出反应。

![image](https://github.com/janwee-sha/reading-notes/blob/main/Front-end/React.in.Action.Graph.2-11.png)

除了保留的生命周期方法，使用者可以对React类添加自己的方法。

### 2.3.1 A React state of mind

与自定义方法和生命周期方法一起，React类还提供了能够与组件一起持久化的状态（数据）。

React中，那些通过扩展`React.Component`并作为JavaScript类创建的组件可能既有**可变状态**也有**不可变状态**，基于函数创建的组件（无状态函数组件）则只能访问**不可变状态**。

在扩展自`React.Component`的组件中，可以通过类实例的`this.state`属性访问可变状态，而不可变状态则是通过`this.props`进行访问的。

### 2.3.2 Setting initial state

可以使用组件的构造函数来为组件设置初始状态。

```
class YourComponent extends {
    constructor(props) {
        this.state = {...}
    }
}
```

需要使用一个专门的方法来更新组件类的构造函数中初始化的状态。可以使用`this.setState`来更新React类组件中的状态。

`this.setState`接收一个用来更新状态的更新器函数，而且它不返回任何东西：

```
setState(
    function(prevState, props) -> nextState,
    callback
) -> void
```

React实现了一个合成事件系统作为虚拟DOM的一部分，它会将浏览器中的事件转换为React应用中的事件。

用法如下：

```
class YourComponent extends {
    constructor(props) {
        this.handleUserChange = this.handleUserChange.bind(this);//由于使用类创建的组件我无法自动绑定组件的
        //方法，因此需要在构造函数中将它们绑定到this上
    }
    
    handleUserChange(event) {//指定事件处理器来响应事件
        ...
    }
}
```

