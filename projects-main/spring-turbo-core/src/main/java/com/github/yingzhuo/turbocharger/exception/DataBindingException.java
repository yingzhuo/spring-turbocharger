package com.github.yingzhuo.turbocharger.exception;

import com.github.yingzhuo.turbocharger.databinding.MultiMessageSourceResolvable;
import com.github.yingzhuo.turbocharger.messagesource.SimpleMessageSourceResolvable;
import com.github.yingzhuo.turbocharger.messagesource.StringMessageSourceResolvable;
import com.github.yingzhuo.turbocharger.util.StringFormatter;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import com.github.yingzhuo.turbocharger.util.collection.iterator.NoopComparator;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.*;
import java.util.stream.Collectors;

public final class DataBindingException extends IllegalArgumentException implements MultiMessageSourceResolvable {

	private final List<MessageSourceResolvable> innerList = new ArrayList<>();

	private DataBindingException() {
		super();
	}

	public static void raiseIfNecessary(@Nullable Errors errors) {
		if (errors != null && errors.hasErrors()) {
			var ex = new DataBindingException();

			CollectionUtils.nullSafeAddAll(
				ex.innerList,
				errors.getAllErrors()
					.stream()
					.map(objectError -> (MessageSourceResolvable) objectError)
					.toList()
			);

			throw ex;
		}
	}

	public static void raiseIfNecessary(@Nullable BindingResult bindingResult) {
		raiseIfNecessary((Errors) bindingResult);
	}

	public static DataBindingException of(String firstMessage, String... moreMessages) {
		List<String> messages = new ArrayList<>();
		StringUtils.blankSafeAdd(messages, firstMessage);
		StringUtils.blankSafeAddAll(messages, moreMessages);

		var ex = new DataBindingException();
		ex.innerList.addAll(
			messages.stream()
				.map(StringMessageSourceResolvable::new)
				.toList()
		);
		return ex;
	}

	public static DataBindingException of(MessageSourceResolvable firstMessage, MessageSourceResolvable... moreMessages) {
		List<MessageSourceResolvable> messages = new ArrayList<>();
		CollectionUtils.nullSafeAdd(messages, firstMessage);
		CollectionUtils.nullSafeAddAll(messages, moreMessages);

		var ex = new DataBindingException();
		ex.innerList.addAll(messages);
		return ex;
	}

	public static DataBindingException of(Collection<MessageSourceResolvable> messageSourceResolvableCollection) {
		var ex = new DataBindingException();
		CollectionUtils.nullSafeAddAll(ex.innerList, messageSourceResolvableCollection);
		return ex;
	}

	public static DataBindingException ofCode(String code) {
		return ofCode(code, null, null);
	}

	public static DataBindingException ofCode(String code, @Nullable Object... arguments) {
		return ofCode(code, arguments, null);
	}

	public static DataBindingException ofCode(String code, @Nullable Object[] arguments, @Nullable String defaultMessage) {
		Assert.hasText(code, "code is required");
		var ex = new DataBindingException();
		ex.innerList.add(new SimpleMessageSourceResolvable(code, arguments, defaultMessage));
		return ex;
	}

	public static DataBindingException ofFormattedMessage(String messageTemplate, Object... args) {
		return of(StringFormatter.format(messageTemplate, args));
	}

	@Override
	public Iterator<MessageSourceResolvable> iterator() {
		return innerList.iterator();
	}

	public int size() {
		return innerList.size();
	}

	public boolean isEmpty() {
		return innerList.isEmpty();
	}

	public boolean isNotEmpty() {
		return !innerList.isEmpty();
	}

	public List<String> getMessages(MessageSource messageSource) {
		return getMessages(messageSource, null, null);
	}

	public List<String> getMessages(MessageSource messageSource, @Nullable Locale locale) {
		return getMessages(messageSource, locale, null);
	}

	public List<String> getMessages(MessageSource messageSource, @Nullable Locale locale, @Nullable Comparator<String> ordering) {
		return stream()
			.map(msr -> AbstractMessageResolvableException.getMessage(messageSource, msr, locale))
			.sorted(Objects.requireNonNullElseGet(ordering, NoopComparator::getInstance))
			.collect(Collectors.toList());
	}

}
