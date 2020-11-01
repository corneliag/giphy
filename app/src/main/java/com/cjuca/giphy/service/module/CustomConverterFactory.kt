package com.cjuca.giphy.service.module

import com.cjuca.giphy.service.model.Data
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return try {
            val dataType = TypeToken.getParameterized(Data::class.java, type).type
            val converter: Converter<ResponseBody, Data<Any>>? =
                retrofit.nextResponseBodyConverter(this, dataType, annotations)
            DataConverter(converter)
        } catch (e: Exception) {
            null
        }
    }
}

class DataConverter<Any>(
    private val delegate: Converter<ResponseBody, Data<Any>>?
) : Converter<ResponseBody, Any> {
    override fun convert(value: ResponseBody): Any? {
        val graphQLDataModel = delegate?.convert(value)
        return graphQLDataModel?.data
    }
}