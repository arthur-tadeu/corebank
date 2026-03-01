/**
 * <h1>CoreBank - Strategic Digital Finance Platform</h1>
 * <p>
 * This project implements the <b>Core Banking Bounded Context</b>, serving as
 * the
 * authoritative source of truth for account lifecycles and monetary integrity.
 * </p>
 *
 * <h2>Architectural Decision Record (ADR): Strategic Boundaries</h2>
 * <p>
 * <b>Context Ownership:</b> This system owns the <i>Account Aggregate</i>,
 * balance
 * consistency, and transaction recording. It intentionally does NOT own
 * external
 * concerns such as Fraud Detection, Payment Clearing, or Notification delivery.
 * </p>
 *
 * <h2>Evolutionary & Resilient Design</h2>
 * <p>
 * <b>Aggregate Consistency Boundary:</b> We enforce <b>Strong Consistency</b>
 * within the
 * Aggregate (e.g., Account balance), while preparing for <b>Eventual
 * Consistency</b>
 * across boundaries via Domain Events.
 * </p>
 *
 * <p>
 * <b>Scalability Path:</b> The clear separation between Domain Core and
 * Delivery Mechanisms
 * (REST, Web UI) allows this system to evolve from a modular monolith into a
 * distributed Microservices architecture without rewriting business rules.
 * </p>
 *
 * <h3>Dependency Rule Enforcement</h3>
 * <p>
 * High-integrity financial systems require strict inward-pointing dependencies.
 * The Domain holds <b>Domain Authority</b> and must never depend on
 * Infrastructure
 * or Presentation adapters.
 * </p>
 */
package com.corebank;
