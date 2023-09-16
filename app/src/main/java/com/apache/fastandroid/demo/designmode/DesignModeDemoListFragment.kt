package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import com.apache.fastandroid.databinding.FragmentDesignModeListBinding
import com.apache.fastandroid.demo.bean.PersonDto
import com.apache.fastandroid.demo.bean.ToneDto
import com.apache.fastandroid.demo.designmode.absfactory.ConcreteAnimalFactory
import com.apache.fastandroid.demo.designmode.adapter.*
import com.apache.fastandroid.demo.designmode.base.AnimalType
import com.apache.fastandroid.demo.designmode.bridge.ConcreteImplementor1
import com.apache.fastandroid.demo.designmode.bridge.ConcreteImplementor2
import com.apache.fastandroid.demo.designmode.bridge.RefinedAbstraction1
import com.apache.fastandroid.demo.designmode.bridge.RefinedAbstraction2
import com.apache.fastandroid.demo.designmode.builder.Person
import com.apache.fastandroid.demo.designmode.builder.Person2
import com.apache.fastandroid.demo.designmode.chain.FInterceptorChain
import com.apache.fastandroid.demo.designmode.chain.FRequest
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor1
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor2
import com.apache.fastandroid.demo.designmode.chain.interceptors.CustomInterceptor3
import com.apache.fastandroid.demo.designmode.chain2.HeaderHandler
import com.apache.fastandroid.demo.designmode.chain2.Interceptor
import com.apache.fastandroid.demo.designmode.chain2.LoggingHandler
import com.apache.fastandroid.demo.designmode.chain2.Request
import com.apache.fastandroid.demo.designmode.command.Light
import com.apache.fastandroid.demo.designmode.command.Switch
import com.apache.fastandroid.demo.designmode.command.TurnOffCommand
import com.apache.fastandroid.demo.designmode.command.TurnOnCommand
import com.apache.fastandroid.demo.designmode.composite.Composite
import com.apache.fastandroid.demo.designmode.composite.Leaf
import com.apache.fastandroid.demo.designmode.decorator.ConcreteComponent
import com.apache.fastandroid.demo.designmode.decorator.ConcreteDecoratorA
import com.apache.fastandroid.demo.designmode.decorator.ConcreteDecoratorB
import com.apache.fastandroid.demo.designmode.facade.MediaPlayer
import com.apache.fastandroid.demo.designmode.factory.AnimalFactory
import com.apache.fastandroid.demo.designmode.filter.*
import com.apache.fastandroid.demo.designmode.interceptor.AddExpression
import com.apache.fastandroid.demo.designmode.interceptor.Context
import com.apache.fastandroid.demo.designmode.interceptor.NumberExpression
import com.apache.fastandroid.demo.designmode.mediator.ConcreteColleague1
import com.apache.fastandroid.demo.designmode.mediator.ConcreteColleague2
import com.apache.fastandroid.demo.designmode.mediator.ConcreteMediator
import com.apache.fastandroid.demo.designmode.observer.ConcreteObserver
import com.apache.fastandroid.demo.designmode.observer.ConcreteSubject
import com.apache.fastandroid.demo.designmode.proxy.ServiceApi
import com.apache.fastandroid.demo.designmode.proxy.ServiceApiV2
import com.apache.fastandroid.demo.designmode.proxy.dynamic.ISubject
import com.apache.fastandroid.demo.designmode.proxy.dynamic.ProxyHandler
import com.apache.fastandroid.demo.designmode.proxy.dynamic.ProxySubject
import com.apache.fastandroid.demo.designmode.proxy.dynamic.RealSubject
import com.apache.fastandroid.demo.designmode.singleton.SingleInstanceSync
import com.apache.fastandroid.demo.designmode.singleton.SingleObject
import com.apache.fastandroid.demo.designmode.singleton.SingletonByObject
import com.apache.fastandroid.demo.designmode.state.Machine
import com.apache.fastandroid.demo.designmode.strategy.CreditCardStrategy
import com.apache.fastandroid.demo.designmode.strategy.PayPalStrategy
import com.apache.fastandroid.demo.designmode.strategy.ShoppingOrder
import com.apache.fastandroid.demo.designmode.template.ConcreteClassA
import com.apache.fastandroid.demo.designmode.template.ConcreteClassB
import com.apache.fastandroid.demo.designmode.visitor.ConcreteVisitor
import com.apache.fastandroid.demo.designmode.visitor.ObjectStructure
import com.apache.fastandroid.demo.designmode.wrapper.AContext
import com.apache.fastandroid.demo.designmode.wrapper.AContextWrapper
import com.apache.fastandroid.demo.designmode.wrapper.MyToast
import com.apache.fastandroid.demo.memento.Caretaker
import com.apache.fastandroid.demo.memento.Originator
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/3/11.
 */
class DesignModeDemoListFragment:BaseBindingFragment<FragmentDesignModeListBinding>(FragmentDesignModeListBinding::inflate) {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        Logger.d("DesignModeDemoListFragment mHandler:${mHandler}")


        mBinding.btnSingleton.setOnClickListener {
            singletonMode()
        }

        mBinding.btnObserver.setOnClickListener {
            observerMode()
        }

        mBinding.btnFactory.setOnClickListener {
            factoryMode()
        }

        mBinding.btnAbstractFactory.setOnClickListener {
            abstractFactoryMode()
        }

        mBinding.btnBuilder.setOnClickListener {
            buildMode()
        }

        mBinding.btnPrototype.setOnClickListener {
            protoTypeMode()
        }

        mBinding.btnAdapter.setOnClickListener {
            adapterMode()
        }

        mBinding.btnBridge.setOnClickListener {
            bridgeMode()
        }
        mBinding.btnComposite.setOnClickListener {
            compositeMode()
        }

        mBinding.btnDecorator.setOnClickListener {
            decoratorMode()
        }

        mBinding.btnFacade.setOnClickListener {
            facadeMode()
        }

        mBinding.btnFilter.setOnClickListener {
            filterMode()
        }

        mBinding.btnChain.setOnClickListener {
            chainMode()
        }
        mBinding.btnChain2.setOnClickListener {
            chainMode2()
        }

        mBinding.btnCommand.setOnClickListener {
            commandMode()
        }

        mBinding.btnInterceptor.setOnClickListener {
            interceptorMode()
        }

        mBinding.btnProxy.setOnClickListener {
            proxyMode()
        }

        mBinding.btnTemplate.setOnClickListener {
            templateMode()
        }

        mBinding.btnStrategy.setOnClickListener {
            strategyMode()
        }

        mBinding.btnVisitor.setOnClickListener {
            visitorMode()
        }

        mBinding.btnMediator.setOnClickListener {
            mediatorMode()
        }

        mBinding.btnMenento.setOnClickListener {
            mementoMode()
        }

        mBinding.btnState.setOnClickListener {
            stateMode()
        }

    }


    /**
     * 状态模式（State Pattern）是一种行为设计模式，它允许对象在内部状态发生变化时改变它的行为。
     * 通过将对象的状态和相应的行为封装成一个个独立的类，使得在不同的状态下可以轻松地切换对象的行为，同时也使对象的维护更加简单。

    状态模式包含以下几个要素：

    状态接口（State Interface）：定义了表示对象状态的接口，通常包含多个方法对对象进行操作。
    具体状态类（Concrete State Classes）：实现了状态接口，并且包含了特定状态下的行为逻辑。
    上下文类（Context Class）：持有一个当前状态对象，并且提供了一些操作方法，允许客户端程序在不同状态下使用不同的行为。
    当一个对象的行为需要根据其状态的变化而改变时，可以使用状态模式来实现。比如，一个订单对象在支付前、支付中、已支付、已发货等状态下，需要采取不同的行动，这时候就可以使用状态模式来实现。

    状态模式的优点包括：

    可以避免大量的条件语句：将不同状态下的行为逻辑分别放在不同的状态类中，可以避免出现大量的 if/else 判断语句。
    可以方便地增加新的状态：每个状态对象是独立的，可以方便地增加新的状态，而不需要修改已有的代码。
    可以让状态转换更加明确：状态模式中定义了状态转换的接口和逻辑，并且将其封装在状态类中，使得状态转换更加明确。
    但是状态模式也有一些缺点，比如：

    状态类之间的转换逻辑可能比较复杂，需要仔细设计。
    如果状态转换频繁发生，则可能导致状态类对象数量增多，从而增加系统的复杂性。
     */
    private fun stateMode() {
        Machine.turnOn()
        Machine.turnOn()
        Machine.turnOff()
        Machine.turnOff()
    }

    /**
     * 备忘录模式是一种设计模式，它允许在不破坏封装性的前提下捕获一个对象
     * 的内部状态，并在该对象之外保存这个状态。这样就可以将对象恢复到之前的
     * 某个状态。备忘录模式通常用于实现“撤销”操作，以及在不破坏封装性的情况
     * 下，在对象之外保存其状态。
     */
    private fun mementoMode() {

        val originator = Originator("State1")
        val caretaker = Caretaker()

        //保存原始状态
        caretaker.addMemento(originator.createMemento())

        originator.state = "State2"

        //保存新状态
        caretaker.addMemento(originator.createMemento())

        //恢复到之前的状态
        originator.restoreMemento(caretaker.getMemento(0))
        println(originator.state) // 输出：State1
    }

    /**
     * 中介者模式是一种行为型设计模式，它允许对象之间通过中介者对象进行通信，而不必直接引用彼此。
     * 中介者对象封装了对象之间的交互，从而使得对象之间的耦合度降低。
     * 这种模式被广泛应用于图形用户界面（GUI）开发和分布式系统中的消息传递系统。
     * 在中介者模式中，多个对象之间不直接相互通信，而是通过一个中介者对象来协调它们的交互。
     * 这样可以避免对象之间的紧密耦合，使得系统更加灵活，并且易于维护和扩展。
     */
    private fun mediatorMode() {

        val mediator = ConcreteMediator()
        val colleague1 = ConcreteColleague1("zhangsan",mediator)
        val colleague2 = ConcreteColleague2("lisi",mediator)

        mediator.setColleague1(colleague1)
        mediator.setColleague2(colleague2)

        colleague1.send("hello, lisi")
        colleague2.send("hello, zhangsan")
    }

    /**
     * 访问者模式
     * 访问者模式是一种行为设计模式，它可以在不改变对象结构的前提下定义对象新的操作。该模式将算法与对象结构分离开来，使得算法可以独立于对象结构而变化。
       在访问者模式中，定义了一个访问者对象和一个被访问的对象结构。访问者对象可以访问并处理该对象结构中的各个元素，从而实现对该对象结构中各个元素的不同处理方式。
       被访问的对象结构则提供了一组接收访问者对象的接口，以便访问者对象能够访问自己中的元素。
       通过使用访问者模式，我们可以将对象结构与对其进行操作的算法分离出来，从而简化对象结构的操作，并且可以方便地添加新的操作，
       而无需修改对象结构本身。这使得访问者模式非常适合用于处理复杂的对象结构，例如树形结构或图形结构等。
     */
    private fun visitorMode() {
        val objectStructure = ObjectStructure()
        val visitor = ConcreteVisitor()
        objectStructure.accept(visitor)
    }


    /**
     * 策略模式是一种设计模式，它允许在运行时动态地改变算法或行为。该模式定义了一系列算法，将每个算法都封装起来，并使它们可以互换使用。这使得客户端代码可以在不影响其结构的情况下更改算法或行为。
     * 在策略模式中，通常会定义一个抽象策略类和具体的实现策略类。客户端代码将抽象策略类作为参数传递，在运行时将其替换为具体的实现策略类。这样客户端代码就可以使用不同的策略来达到不同的结果。
     * 策略模式的优点包括提高代码复用性、可维护性和可扩展性。同时，它还遵循开闭原则，即对于扩展开放、对于修改封闭，因此更容易进行代码的维护和扩展。
     */
    private fun strategyMode() {
        val order1 = ShoppingOrder(100.0, CreditCardStrategy("John Doe", "1234567890123456", "123"))
        order1.processOrder()

        val order2 = ShoppingOrder(200.0, PayPalStrategy("john.doe@example.com", "password123"))
        order2.processOrder()
    }

    /**
     * 模板方法模式（Template Method Pattern）是一种行为型设计模式，它定义了一个操作中算法的基本骨架，将一些步骤延迟到子类中实现。这样可以在不改变算法结构的情况下，通过重写某些步骤来改变算法的行为。

    在模板方法模式中，把不变的行为放在父类中实现，而把可变的行为留给子类来实现。具体而言，在模板方法模式中，通常会定义一个抽象类，其中包含一个模板方法和一些抽象方法。模板方法通常包括一系列算法步骤，而这些步骤中的一部分可以由子类实现。这样就可以通过继承来定制算法的某些部分。

    模板方法模式的优点在于，它提供了一种简单的方法来实现代码复用和扩展现有代码的能力。同时，它还允许父类控制算法的结构，确保所有实现都遵循相同的流程，并且每个步骤都得到正确的执行。
     */
    private fun templateMode() {
        val concreteA = ConcreteClassA()
        val concreteB = ConcreteClassB()
        concreteA.doSomething()

        println("-------")
        concreteB.doSomething()
    }

    private fun proxyMode() {
        // 创建目标对象和代理对象
        val realSubject = RealSubject()
        val proxy = ProxySubject(realSubject)

        // 使用代理对象来处理请求
        proxy.request()

        val serviceApiV2 = ServiceApiV2(ServiceApi())
        serviceApiV2.doSomething()


        val proxyHandler = ProxyHandler(realSubject)
        val subject = proxyHandler.proxyClass as ISubject
        subject.request()
    }

    /**
     * 参考: https://juejin.cn/post/6858167650036973582
     * 解释器模式是一种行为型设计模式，它可以根据特定的语法规则解释并执行给定的语言。
     * 该模式通常用于处理自然语言或类似的领域特定语言。

    在解释器模式中，有一个抽象表达式类定义了一个解释方法，该方法接收上下文对象作为参数，并以某种方式对其进行操作。
    具体的表达式子类将实现该方法，从而提供特定的解释逻辑。

    另外，解释器模式还包括一个上下文类，该类包含要解释的语言、变量值等信息。解释器对象将使用这些上下文信息来解释和计算表达式。

    总之，解释器模式可用于处理复杂语言或运算符，并将其转换为计算机可识别的格式，以便进行进一步的处理和操作。
     */
    private fun interceptorMode() {
// 构造解释器对象
        val context = Context("1+2", 0)
        val expression = AddExpression(NumberExpression(1), NumberExpression(3))

        // 解释并计算结果
        expression.interpret(context)
        println(context.output) // 输出 3
    }

    /**
     * 命令行模式是一种面向对象设计模式，它允许将请求封装为对象，并将其作为参数传递给其他对象，从而在不同的上下文中使用命令。
     * 命令模式的核心思想是将请求的发送者与接收者解耦，使得发送者只需知道如何发送请求，而不需要知道请求具体如何被执行以及由谁来执行。
     * 这种解耦可以带来更好的扩展性、可维护性和测试性。
        在命令模式中，有四个主要的角色：
        Command（命令）：声明了抽象的执行操作接口，并包含了一个执行该操作的方法。
        ConcreteCommand（具体命令）：实现了Command接口，并将一个接收者对象绑定到一个动作。
        Invoker（调用者）：负责调用命令对象来执行请求。
        Receiver（接收者）：知道如何实施与执行一个请求相关的操作。

        当客户端需要执行某个操作时，它会创建一个特定的命令对象，并将其与一个接收者对象进行绑定。然后，客户端将该命令对象传递给调用者对象，并通过调用者来触发命令对象的执行。这样，命令对象就可以在接收者对象上执行操作了。
        命令模式可以用于实现撤销/重做、事务性操作、日志记录、队列等功能。它在MVC架构、菜单管理和插件系统中也有广泛的应用。
     */
    private fun commandMode() {
        val light = Light()
        val turnOnCommand = TurnOnCommand(light)
        val turnOffCommand = TurnOffCommand(light)

        val switch = Switch(turnOnCommand, turnOffCommand)

        switch.turnOn() // 输出：Light is on
        switch.turnOff() // 输出：Light is off
    }

    /**
     *
     */
    private fun chainMode2() {
        val interceptor = Interceptor()
        interceptor.addHandler(LoggingHandler())
        interceptor.addHandler(HeaderHandler())

        val request = Request("https://www.example.com")
        val response = interceptor.execute(request)
        println(response)
    }

    /**
     * 责任链模式（Chain of Responsibility Pattern）是一种行为型设计模式，它允许多个对象可以处理相同的请求，并将这些对象组成一个链，请求沿着这条链传递，直到有一个对象处理它为止。每个对象都有机会来处理请求，但是它并不知道请求是否会被后续的对象处理。
     * 这种模式通常用于需要动态决定哪些对象处理请求的情况下。例如，当你需要根据不同的条件选择不同的处理器时，责任链模式就可以派上用场。
     * 在责任链模式中，每个处理器都有一个指向下一个处理器的引用。如果当前处理器无法处理请求，则它会将请求转发给下一个处理器。这样的处理方式类似于水管中的阀门，请求就像水流一样顺着管子流过去，直到找到一个能够处理它的对象。
     */
    private fun chainMode() {
        val intercptors = listOf(CustomInterceptor1(), CustomInterceptor2(), CustomInterceptor3())
        var fRequest = FRequest("text", "www.baidu.com")
        val chain = FInterceptorChain(intercptors,0, fRequest)
        chain.procced(fRequest)
    }


    /**
     * 过滤器模式（Filter Pattern）是一种常见的软件设计模式，它可以通过多个过滤器按照一定规则对对象进行筛选和过滤。
     * 该模式通过将过滤条件封装在一个过滤器对象中，然后将这些过滤器组合起来使用，以实现更灵活、可扩展的过滤操作。
     *  在应用程序中，过滤器模式通常被用来处理一些集合数据，比如数据库查询结果、文件列表等。通过定义不同类型的过滤器，我们可以根据不同的需求对这些集合数据进行筛选和过滤，从而满足具体的业务需求。例如，在电商网站中，我们可以定义一个价格过滤器、一个品牌过滤器、一个销量过滤器等等，让用户根据自己的需求来筛选商品列表。
     *  过滤器模式的优点在于其结构清晰、扩展性好、可重用性强，同时也能够有效地降低系统耦合度，使得各个部分可以独立进行修改和测试。
     */
    private fun filterMode() {
        val persons = listOf(
            FPerson("Alice", 25, "female"),
            FPerson("Bob", 30, "male"),
            FPerson("Charlie", 35, "male"),
            FPerson("David", 40, "male"),
            FPerson("Eve", 45, "female")
        )
        val ageFilter = AgeFilter(30, 40)
        val genderFilter = GenderFilter("male")
        val nameFilter = NameFilter("a")

        val filterChain = FilterChain()
            .addFilter(ageFilter)
            .addFilter(genderFilter)
            .addFilter(nameFilter)

        val filteredPersons = filterChain.filter(persons)
        filteredPersons.forEach { println(it) }

    }

    /**
     * 外观模式（Facade Pattern）是一种结构型设计模式，它提供了一个简单的接口，为复杂的子系统提供一个统一的入口点。
    外观模式通过将复杂的子系统封装在一个外观类中，隐藏了子系统的复杂性，客户端只需要与外观类交互，而无需直接与子系统中的各个组件交互。这样可以降低客户端与子系统之间的耦合度，使得客户端更加容易使用和维护该系统。
    外观模式通常包含一个外观类和一些子系统组件。外观类负责协调和管理子系统中的各个组件，向客户端提供一个统一的接口。

    外观模式的优点包括：

    简化客户端使用：外观模式隐藏了子系统的复杂性，提供了一个简单的接口，使得客户端更容易使用该系统。
    降低耦合度：外观模式将客户端与子系统解耦，减少了客户端对子系统组件的依赖关系，从而使得系统更加灵活和易于修改和维护。
    提高了安全性：外观模式可以限制客户端访问子系统的某些组件，从而提高了系统的安全性。
    不过，外观模式也存在一些缺点，例如：

    可能会造成系统的不必要的复杂性：如果外观类设计得不好，可能会增加系统中的组件数量和复杂性。
    不符合开闭原则：当需要修改系统时，如果外观类不能满足客户端的需求，那么就需要修改外观类及其子系统组件，这在一定程度上违反了开闭原则。
     *
     */
    private fun facadeMode() {
        val player = MediaPlayer()

        player.playAudio()
        player.playVideo()
        player.playOgg()
    }


    /**
     * 装饰器模式是一种结构型设计模式，它允许在不改变对象自身的基础上，动态地给对象添加额外的职责。
     * 它通过将对象放入包含行为的特殊对象包装器中来实现此功能。这个特殊的包装器类称为装饰器。
     *  在装饰器模式中，装饰器类和被装饰的类都实现了相同的接口。装饰器类拥有一个指向被装饰对象的引用，
     *  并在调用相应方法时将调用传递给被装饰对象。装饰器可以在被装饰对象的行为前后添加自己的行为，从而改变被装饰对象的行为。
     *  装饰器模式的优点是可以在运行时动态地添加或删除对象的职责，而无需修改它们的源代码。同时，它也避免了在设计时就要考虑
     *   所有可能的职责组合的问题。
     *   一个经典的例子是在咖啡店中添加各种调料的功能。在这个例子中，被装饰的类是咖啡，而装饰器类是各种调料，如奶油、糖浆等。
     *   装饰器可以将调料添加到咖啡中，并改变咖啡的味道。同时，我们可以在不同的装饰器之间自由组合，从而实现各种不同的口味。
     */
    private fun decoratorMode() {
        val component = ConcreteComponent()

        val decoratorA = ConcreteDecoratorA(component)
        val decoratorB = ConcreteDecoratorB(component)

        decoratorA.operation()
        decoratorB.operation()


//        AContextWrapper(AContext()).doSomething1()
//
//        MyToast.makeText(context,"I am toast", Toast.LENGTH_SHORT).show()



    }


    /**
     * 组合模式是一种结构型设计模式，它允许将对象组合成树形结构来表示“部分-整体”的层次结构。
     * 在组合模式中，单个对象和组合对象都实现了相同的接口，从而使它们可以被同等地对待。
     * 这种方式使得客户端代码可以以一致的方式处理单个对象和组合对象，从而使代码更加简单、灵活和可扩展。
     *  在组合模式中，树形结构由两种基本元素组成：叶子节点和组合节点。叶子节点表示树形结构中的最小单位，
     *  它们不包含任何子节点；而组合节点包含其他子节点，它们可以是叶子节点或其他组合节点。这种层次结构可以无限地延伸下去，
     *  形成复杂的树形结构。
     *  使用组合模式，可以将单个对象和组合对象看作是同一个对象，从而使代码更加简洁和易于维护。它通常应用于以下场景：


      当需要表示一个对象的部分-整体层次结构时，可以使用组合模式。

    当希望客户端能够以一致的方式处理单个对象和组合对象时，可以使用组合模式。

    当需要对树形结构进行遍历时，可以使用组合模式来实现迭代器模式。
    下面是一个组合模式的示例代码，用于表示一个组织机构的树形结构：
     */
    private fun compositeMode() {
        val root = Composite("总公司")
        val sub1 = Composite("分公司1")
        val sub2 = Composite("分公司2")

        root.add(sub1)
        root.add(sub2)

        val leaf1 = Leaf("部门1")
        val leaf2 = Leaf("部门2")
        val leaf3 = Leaf("部门3")

        sub1.add(leaf1)
        sub1.add(leaf2)

        sub2.add(leaf3)

        root.operation()
    }

    /**
     * 桥接模式是一种结构型设计模式，它将抽象部分与实现部分分离，使它们可以独立地变化。该模式的主要思想是将一个大类或一组类拆分
     * 成两个独立的部分，即抽象部分和实现部分，从而使它们可以独立地变化和扩展，而不会相互影响。
      在桥接模式中，抽象部分和实现部分分别由抽象类和实现类来实现，它们之间通过一个桥接接口进行交互。
      抽象部分定义了一组抽象方法来描述自身的行为，而实现部分则实现了这些抽象方法，并提供了一些具体的操作。
      桥接接口定义了抽象部分和实现部分之间的沟通接口，使它们可以独立地进行扩展和变化。
      使用桥接模式，可以实现系统的高内聚、低耦合，同时也可以方便地进行系统的扩展和修改。它通常应用于以下场景：
      当一个类存在多个变化维度时，可以使用桥接模式将这些维度分离，从而使它们可以独立地变化和扩展。
      当一个类需要在运行时切换多个不同的实现时，可以使用桥接模式来实现该功能。
      当一个类需要与多个不同的类进行协作时，可以使用桥接模式来减少类之间的耦合度。

     */
    private fun bridgeMode() {
        val implementor1 = ConcreteImplementor1()
        val implementor2 = ConcreteImplementor2()

        val abstract1 = RefinedAbstraction1(implementor1)
        val abstract2 = RefinedAbstraction2(implementor2)
        abstract1.operation()
        abstract2.operation()
    }

    /**
     * 适配器模式是一种结构型设计模式，它的主要作用是将一个类的接口转换成客户端所期望的另一个接口，
     * 从而使得原本不兼容的类可以一起工作。适配器模式通常用于新旧系统之间的接口兼容问题。
    在适配器模式中，适配器类起到了“适配”的作用，它包装了一个已有的类，并将它的接口转换成客户端所需要的接口。
      适配器类可以通过继承或组合的方式实现。如果适配器类通过继承已有类的方式实现，那么它称为类适配器；
      如果适配器类通过组合已有类的方式实现，那么它称为对象适配器。
    UML 类图

    +------------------+       +------------------+
    |      Target      |       |     Adaptee      |
    +------------------+       +------------------+
    | +request()       |       | +specificRequest()|
    +------------------+       +------------------+
    /\                        |
    |                         |
    |                         |
    +------------------+       +------------------+
    |  ClassAdapter    |       |  ObjectAdapter   |
    +------------------+       +------------------+
    |                  |       | +adaptee: Adaptee |
    |                  |       +------------------+
    |                  |       | +request()       |
    | +request()       |       +------------------+
    +------------------+

     在上面的 UML 类图中，Target 是客户端所期望的接口，定义了一个 request() 方法。
     Adaptee 是需要被适配的已有类，定义了一个不符合客户端所期望接口的 specificRequest() 方法。
    ClassAdapter 是通过继承已有类的方式实现的适配器类，它继承了 Adaptee 类并实现了 Target 接口。
    ObjectAdapter 是通过组合已有类的方式实现的适配器类，它包含一个 Adaptee 类的实例，并实现了 Target 接口。
     */
    private fun adapterMode() {
        val classAdapter = ClassAdaptee()
        classAdapter.request()

        val objectAdapter = ObjectAdaptee(Adaptee())
        objectAdapter.request()


        val personAdapter = PersonAdapter()
        val ttsRoleList1 = personAdapter.adapter(PersonDto())


        val toneAdapter = ToneAdapter()
        val ttsRoleList2 = toneAdapter.adapter(ToneDto())

    }

    /**
     * 原型模式是一种创建型设计模式，它通过克隆已有对象来创建新对象，而不是通过实例化一个新的对象。
     * 原型模式使用原型对象作为蓝图，然后在需要创建新对象时，复制该原型对象并进行必要的修改，以生成新的对象。
     */
    private fun protoTypeMode() {
// 创建一个 Person 对象作为原型
        val originalPerson = Person.Builder().setName("zhangsan").setAge(18).setAddress("shenzhen").build()
        val clonedPerson = originalPerson.clone()
        // 输出克隆后的对象
        println(clonedPerson)
    }

    /**
     * 建造者模式
     */
    private fun buildMode() {
        val person = Person.Builder().setName("zhangsan").setAge(18).setAddress("shenzhen").build()
        Logger.d("person name:${person.name}")

        val newPerson = person.newBuilder().setName("lisi").build()
        Logger.d("newPerson name:${newPerson.name}")


        val person2 = Person2.Builder.obtain().build()



    }

    /**
     *  抽象工厂模式
     */
    private fun abstractFactoryMode() {
        val dog = ConcreteAnimalFactory().createDog()
        val cat = ConcreteAnimalFactory().createCat()
        dog.makeSound()
        cat.makeSound()
    }

    /**
     * 工厂模式
     */
    private fun factoryMode() {
        val dog = AnimalFactory.createAnimal(AnimalType.DOG)
        dog?.makeSound() // Output: Woof!

        val cat = AnimalFactory.createAnimal(AnimalType.CAT)
        cat?.makeSound() // Output: Meow!
    }


    /**
     * 观察者模式
     */
    private fun observerMode() {
        val subject = ConcreteSubject()
        val observer1 = ConcreteObserver("zhangsan")
        val observer2 = ConcreteObserver("lisi")


        subject.attach(observer1)
        subject.attach(observer2)

        subject.doSomeLogic()

        subject.detach(observer1)
        subject.doSomeLogic()




    }

    /**
     * 单例模式
     */
    private fun singletonMode() {
        SingletonByObject.count()
        SingleInstanceSync.getInstance()
        SingleObject.getInstance()
    }
}