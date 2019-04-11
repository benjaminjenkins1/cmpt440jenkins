# State Machine NPC Project

This project explores the use of state machines with hierarchically nested states to model game NPC's.

The state nesting concept used in these NPC's is described [here](https://en.wikipedia.org/wiki/UML_state_machine#Hierarchically_nested_states):

> The semantics associated with state nesting are as follows : If a system is in the nested state, for example "result" (called the substate), it also (implicitly) is in the surrounding state "on" (called the superstate). This state machine will attempt to handle any event in the context of the substate, which conceptually is at the lower level of the hierarchy. However, if the substate "result" does not prescribe how to handle the event, the event is not quietly discarded as in a traditional "flat" state machine; rather, it is automatically handled at the higher level context of the superstate "on". This is what is meant by the system being in state "result" as well as "on". Of course, state nesting is not limited to one level only, and the simple rule of event processing applies recursively to any level of nesting.

