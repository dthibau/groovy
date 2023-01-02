package org.formation.dynamic

class ChainedInterceptor implements Interceptor {

	def benchmark = new BenchmarkInterceptor()
	def tracer = new TracingInterceptor(writer: new StringWriter())
	@Override
	public Object beforeInvoke(Object object, String methodName, Object[] arguments) {
		benchmark.beforeInvoke(object, methodName, arguments)
		tracer.beforeInvoke(object, methodName, arguments)
	}

	@Override
	public Object afterInvoke(Object object, String methodName, Object[] arguments, Object result) {
		benchmark.afterInvoke(object, methodName, arguments, result)
		tracer.afterInvoke(object, methodName, arguments, result)
	}

	@Override
	public boolean doInvoke() {
		true
	}

}
